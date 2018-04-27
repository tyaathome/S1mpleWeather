package com.tyaathome.s1mpleweather.utils.manager;

import android.annotation.SuppressLint;

import com.tyaathome.s1mpleweather.model.observable.DataObservable;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackUp;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

public class ObservableManager {
//    public static void concat(List<BasePackUp> sources, Action onComplete) {
//        DataObservable observable = new DataObservable(sources);
//        Disposable disposable = Observable
//                .concat(observable.getObservableList())
//                .subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, onComplete);
//    }

    public static void zip(List<BasePackUp> sources, Action onComplete) {
        DataObservable observable = new DataObservable(sources);
        Disposable disposable = Observable.zip(observable.getObservableList(), new Function<Object[], Object>() {
            @Override
            public Object apply(Object[] objects) throws Exception {
                return null;
            }
        }).subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, onComplete);
    }

    public static Observable<?> getZipObservable(List<BasePackUp> sources) {
        DataObservable observable = new DataObservable(sources);

        return Observable.zip(observable.getObservableList(), new Function<Object[], Object>() {
            @Override
            public Object apply(Object[] objects) throws Exception {
                return Arrays.asList(objects);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    public static void concat(List<Observable<?>> sources, Action onComplete) {
        concat(sources, Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, onComplete);
    }

    public static void concat(List<Observable<?>> sources, Consumer<? super Object> onNext, Action onComplete) {
        concat(sources, onNext, Functions.ON_ERROR_MISSING, onComplete);
    }

    @SuppressLint("CheckResult")
    public static void concat(List<Observable<?>> sources, Consumer<? super Object> onNext, Consumer<? super Throwable> onError,
                              Action onComplete) {
        Observable.concat(sources).subscribe(onNext, onError, onComplete);
    }

}
