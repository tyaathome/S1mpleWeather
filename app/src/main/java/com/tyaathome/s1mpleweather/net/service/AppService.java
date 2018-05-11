package com.tyaathome.s1mpleweather.net.service;

import android.content.Context;
import android.widget.Toast;

import com.tyaathome.s1mpleweather.net.factory.MyConverterFactory;
import com.tyaathome.s1mpleweather.net.listener.OnCompleted;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackDown;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackUp;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * app service
 * Created by tyaathome on 2016/4/15.
 */
public class AppService {
    public static AppService instance = null;

    private Retrofit mRetrofit = null;
    private AppAPI mApi = null;

    private AppService() {
        init();
    }

    public static AppService getInstance() {
        if (instance == null) {
            instance = new AppService();
        }
        return instance;
    }

    private void init() {
        if (mRetrofit == null) {
            create();
        }
        if (mApi == null && mRetrofit != null) {
            mApi = mRetrofit.create(AppAPI.class);
        }
    }

    public Retrofit create() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://ztq.soweather.com:8096/")
                .addConverterFactory(MyConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return mRetrofit;
    }

    public AppAPI getApi() {
        if(mApi == null) {
            init();
        }
        return mApi;
    }

    /**
     * 开始请求数据
     *
     * @param context
     * @param request 请求数据包
     * @param onNext  数据请求完成回调
     */
    public void startRequest(final Context context, BasePackUp request, final OnCompleted onNext) {
        init();
        mApi.getData(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BasePackDown>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BasePackDown baseResponse) {
                        Toast.makeText(context, "onNext", Toast.LENGTH_LONG).show();
                        if (baseResponse != null) {
                            Toast.makeText(context, "success!", Toast.LENGTH_LONG).show();
                            if (onNext != null) {
                                onNext.onCompleted(baseResponse);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
