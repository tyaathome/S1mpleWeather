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
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * loading presenter
 * Created by tyaathome on 2018/2/24.
 */

public class LoadingPresenter implements LoadingContract.Presenter {

    private LoadingContract.View mView;
    private Context mContext;
    private static final int RETRY_COUNT = 3;

    public LoadingPresenter(Context context) {
        mContext = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void beginLocation() {
        // 定位observable
        Observable<Integer> locationObservable = Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            LocationTools.getInstance(mContext).startLocation(new LocationTools.OnLocationListener() {
                @Override
                public void onComplete() {
                    if (!emitter.isDisposed()) {
                        emitter.onComplete();
                    }
                }

                @Override
                public void onStop(String errorMessage) {
                    if (!emitter.isDisposed()) {
                        emitter.onError(new RuntimeException(errorMessage));
                    }
                }
            });
        })
                // 设置单次定位的超时时间
                .timeout(5, TimeUnit.SECONDS, Observable.create(emitter ->
                        emitter.onError(new RuntimeException("locationObservable time out"))))
                // 设置重试次数
                .retryWhen(throwableObservable -> throwableObservable
                        .zipWith(Observable.range(1, RETRY_COUNT), (throwable, integer) -> {
                            Log.e("LoadingPresenter", "count:" + String.valueOf(integer));
                            return integer;
                        })
                        // 设置重试等待时间
                        .flatMap(integer -> Observable.timer(1, TimeUnit.SECONDS)));

        // 首页数据请求observable
        Observable dataObservable = Observable.create(emitter -> {
            LocationCityBean city = CityTools.getInstance(mContext).getLocationCity();
            if (city != null) {
                ObservableManager
                        .getZipObservable(AutoDownloadManager.getMainData(city.getId()))
                        .timeout(3, TimeUnit.SECONDS, Observable.empty())
                        .subscribe(new Observer<Object>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Object o) {

                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.gotoNextActivity();
                            }

                            @Override
                            public void onComplete() {
                                mView.gotoNextActivity();
                            }
                        });
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
