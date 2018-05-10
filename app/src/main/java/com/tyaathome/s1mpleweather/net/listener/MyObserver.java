package com.tyaathome.s1mpleweather.net.listener;

import io.reactivex.annotations.NonNull;

public interface MyObserver<T> {

    void onNext(@NonNull T t);
    void onError(@NonNull Throwable e);
    void onComplete();

}
