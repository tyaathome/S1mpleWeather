package com.tyaathome.s1mpleweather.net.listener;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by tyaathome on 2017/10/23.
 */

public abstract class OnCompleteWithDisposable implements OnCompleted {
    public void onSubscribe(@NonNull Disposable d) {

    }
}
