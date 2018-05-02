package com.tyaathome.s1mpleweather.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.tyaathome.s1mpleweather.model.bean.city.LocationCityBean;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;
import com.tyaathome.s1mpleweather.mvp.contract.LoadingContract;
import com.tyaathome.s1mpleweather.utils.manager.AutoDownloadManager;
import com.tyaathome.s1mpleweather.utils.manager.ObservableManager;
import com.tyaathome.s1mpleweather.utils.tools.CityTools;
import com.tyaathome.s1mpleweather.utils.tools.LocationTools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;

/**
 * loading presenter
 * Created by tyaathome on 2018/2/24.
 */

public class LoadingPresenter implements LoadingContract.Presenter {

    private LoadingContract.View mView;
    private Context mContext;
    private static final int RETRY_COUNT = 3;
    private boolean isCompleteLocation = true;

    public LoadingPresenter(Context context) {
        mContext = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void beginLocation() {
        isCompleteLocation = false;
        // 定位observable
        Observable<Integer> locationObservable = Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
                    if (!emitter.isDisposed() && !isCompleteLocation) {
                        LocationTools.getInstance(mContext).startLocation(emitter);
                    }
                }
        ).retryWhen(throwableObservable -> throwableObservable
                .zipWith(Observable.range(1, RETRY_COUNT), (throwable, integer) -> {
                    if (integer >= RETRY_COUNT) {
                        Log.e("LoadingPresenter", "finish" + String.valueOf(integer));
                        isCompleteLocation = true;
                    }
                    Log.e("LoadingPresenter", "count:" + String.valueOf(integer));
                    return integer;
                }).flatMap((Function<Integer, ObservableSource<?>>) integer -> Observable.timer(1, TimeUnit.SECONDS)));

        // 首页数据请求observable
        Observable dataObservable = Observable.create((ObservableOnSubscribe) emitter -> {
            LocationCityBean city = CityTools.getInstance(mContext).getLocationCity();
            if (city != null) {
                ObservableManager
                        .getZipObservable(AutoDownloadManager.getMainData(city.getId()))
                        .subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, () -> emitter.onComplete());
            } else {
                emitter.onComplete();
            }
        });

        List<Observable<?>> observableList = new ArrayList<>();
        observableList.add(locationObservable);
        observableList.add(dataObservable);

        ObservableManager.concat(observableList, () -> mView.gotoNextActivity());
    }

    @Override
    public void requestCityData() {

    }

    @Override
    public void attachView(BaseView view) {
        mView = (LoadingContract.View) view;
    }

    @Override
    public void start() {

    }

}
