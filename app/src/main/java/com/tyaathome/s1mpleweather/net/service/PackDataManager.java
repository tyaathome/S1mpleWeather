package com.tyaathome.s1mpleweather.net.service;

import com.tyaathome.s1mpleweather.net.listener.OnCompleteWithDisposable;
import com.tyaathome.s1mpleweather.net.listener.OnCompleted;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackDown;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackUp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmObject;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by tyaathome on 2017/9/18.
 */

public class PackDataManager {

    /**
     * 数据类型
     */
    private static MediaType STREAM_TYPE = MediaType.parse("application/octet-stream");

    /**
     * 请求数据
     *
     * @param request
     * @param onNext
     */
    public static void startRequest(BasePackUp request, final OnCompleted onNext) {
        AppService.getInstance().getApi().getData(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BasePackDown>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        DisposableManager.getInstance().add(d);
                    }

                    @Override
                    public void onNext(@NonNull BasePackDown BasePackDown) {
                        if (BasePackDown != null) {
                            if (onNext != null) {
                                onNext.onCompleted(BasePackDown);
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

    public static void startRequest(BasePackUp request, final OnCompleteWithDisposable onComplete) {
        AppService.getInstance().getApi().getData(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BasePackDown>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        if (onComplete != null) {
                            onComplete.onSubscribe(d);
                        }
                    }

                    @Override
                    public void onNext(@NonNull BasePackDown BasePackDown) {
                        if (BasePackDown != null) {
                            if (onComplete != null) {
                                onComplete.onCompleted(BasePackDown);
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

    /**
     * 请求数据加数据流
     *
     * @param request
     * @param file
     * @param onNext
     */
    public static void startRequest(BasePackUp request, File file, final OnCompleted onNext) {
        RequestBody requestBody = RequestBody.create(STREAM_TYPE, file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        AppService.getInstance().getApi().getData(filePart, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BasePackDown>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        DisposableManager.getInstance().add(d);
                    }

                    @Override
                    public void onNext(@NonNull BasePackDown BasePackDown) {
                        if (BasePackDown != null) {
                            if (onNext != null) {
                                onNext.onCompleted(BasePackDown);
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

    /**
     * 异步合并请求数据
     *
     * @param sources  上传包列表
     * @param observer 下载回调
     */
    public static void mergeRequest(Iterable<? extends BasePackUp> sources, Observer<RealmObject> observer) {
        List<Observable<RealmObject>> observableList = new ArrayList<>();
        for (BasePackUp up : sources) {
            Observable<RealmObject> observable = AppService.getInstance().getApi().getData(up)
                    //.observeOn(AndroidSchedulers.mainThread())
                    .flatMap((Function
                    <BasePackDown, ObservableSource<RealmObject>>) basePackDown -> Observable.create(emitter -> {
                        String threadName = Thread.currentThread().getName();
                RealmObject object = basePackDown.getData();
                emitter.onNext(object);
                emitter.onComplete();
            }));
            observableList.add(observable);
        }
        Observable.merge(observableList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 异步合并获取缓存数据
     *
     * @param sources  上传包列表
     * @param observer 获取缓存完成回调
     */
    public static void mergeCache(Iterable<? extends BasePackUp> sources, Observer<RealmObject> observer) {
        List<Observable<RealmObject>> observables = new ArrayList<>();
        for (BasePackUp up : sources) {
            Observable<RealmObject> observable = Observable.create(emitter -> {
                RealmObject object = up.getCacheData();
                emitter.onNext(object);
                emitter.onComplete();
            });
            observables.add(observable);
        }
        Observable.merge(observables)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

}
