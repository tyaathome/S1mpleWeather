package com.tyaathome.s1mpleweather.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.tyaathome.s1mpleweather.model.RealmObject.city.LocationCityRealmBean;
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
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
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
    private int currentRetryTime = 0;

    public LoadingPresenter(Context context) {
        mContext = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void beginLocation() {
        isCompleteLocation = false;
        // 定位observable
        Observable<Integer> locationObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                if(!emitter.isDisposed() && !isCompleteLocation) {
                    LocationTools.getInstance(mContext).startLocation(emitter);
                }
            }
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                return throwableObservable.zipWith(Observable.range(1, RETRY_COUNT), new BiFunction<Throwable, Integer, Integer>() {

                    @Override
                    public Integer apply(Throwable throwable, Integer integer) throws Exception {
                        if(integer >= RETRY_COUNT) {
                            Log.e("LoadingPresenter", "finish" + String.valueOf(integer));
                            isCompleteLocation = true;
                        }
                        Log.e("LoadingPresenter", "count:" + String.valueOf(integer));
                        return integer;
                    }
                }).flatMap(new Function<Integer, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Integer integer) throws Exception {
                        return Observable.timer(1, TimeUnit.SECONDS);
                    }
                });
            }
        });

        // 首页数据请求observable
        Observable dataObservable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(final ObservableEmitter emitter) throws Exception {
                LocationCityRealmBean city = CityTools.getInstance(mContext).getLocationCity();
                if(city != null) {
                    ObservableManager
                            .getZipObservable(AutoDownloadManager.getMainData(city.getId()))
                            .subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, new Action() {
                                @Override
                                public void run() throws Exception {
                                    emitter.onComplete();
                                }
                            });
                } else {
                    emitter.onComplete();
                }
            }
        });

        List<Observable<?>> observableList = new ArrayList<>();
        observableList.add(locationObservable);
        observableList.add(dataObservable);

        ObservableManager.concat(observableList, new Action() {
            @Override
            public void run() throws Exception {
                mView.gotoNextActivity();
            }
        });
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
