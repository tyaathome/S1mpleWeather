package com.tyaathome.s1mpleweather.net.service;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * rxjava dispose管理类
 * Created by tyaathome on 2017/9/19.
 */

public class DisposableManager {

    private static DisposableManager instance;
    private CompositeDisposable compositeDisposable;

    private DisposableManager() {
        compositeDisposable = new CompositeDisposable();
    }

    public static DisposableManager getInstance() {
        if(instance == null) {
            instance = new DisposableManager();
        }
        return instance;
    }

    public void add(Disposable disposable) {
        if(compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        compositeDisposable.dispose();
    }

    public int size() {
        return compositeDisposable.size();
    }

}
