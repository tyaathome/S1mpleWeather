package com.tyaathome.s1mpleweather.net.service;

import com.tyaathome.s1mpleweather.net.listener.MyObserver;
import com.tyaathome.s1mpleweather.net.listener.OnCompleteWithDisposable;
import com.tyaathome.s1mpleweather.net.listener.OnCompleted;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackDown;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackUp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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

import static io.reactivex.Observable.create;

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
                            <BasePackDown, ObservableSource<RealmObject>>) basePackDown -> create(emitter -> {
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
            Observable<RealmObject> observable = create(emitter -> {
                RealmObject object = up.getCacheData();
                emitter.onNext(object);
                emitter.onComplete();
            });
            observables.add(observable);
        }
        Observable.merge(observables)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public static void zipRequest(Iterable<? extends BasePackUp> sources, MyObserver<RealmObject[]> observer) {
        List<Observable<RealmObject>> observableList = new ArrayList<>();
        for (BasePackUp up : sources) {
            Observable<RealmObject> observable = AppService.getInstance().getApi().getData(up)
                    .map(BasePackDown::getData);
            observableList.add(observable);
        }
        Observable.zip(observableList, objects -> {
            List<RealmObject> list = new ArrayList<>();
            for (Object o : objects) {
                if (o instanceof RealmObject) {
                    list.add((RealmObject) o);
                }
            }
            return list.toArray(new RealmObject[list.size()]);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RealmObject[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RealmObject[] realmObject) {
                        if (observer != null) {
                            observer.onNext(realmObject);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (observer != null) {
                            observer.onError(t);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (observer != null) {
                            observer.onComplete();
                        }
                    }
                });
    }

    public static void zipCache(Iterable<? extends BasePackUp> sources, MyObserver<RealmObject[]> observer) {
        List<Observable<RealmObject>> observables = new ArrayList<>();
        for (BasePackUp up : sources) {
            Observable<RealmObject> observable = Observable.create(new ObservableOnSubscribe<RealmObject>() {
                @Override
                public void subscribe(ObservableEmitter<RealmObject> emitter) throws Exception {
                    RealmObject object = up.getCacheData();
                    if(object != null) {
                        emitter.onNext(object);
                    }
                    emitter.onComplete();
                }
            })
                    .observeOn(AndroidSchedulers.mainThread()) //调度线程至主线程进行realmobject拷贝
                    .map(realmObject -> {
                        RealmObject result = realmObject.getRealm().copyFromRealm(realmObject);
                        if(!realmObject.getRealm().isClosed()) {
                            realmObject.getRealm().close();
                        }
                        return result;
                    });
            observables.add(observable);
        }
        Observable.zip(observables, objects -> {
            List<RealmObject> list = new ArrayList<>();
            for (Object object : objects) {
                if (object instanceof RealmObject) {
                    list.add((RealmObject) object);
                }
            }
            return list.toArray(new RealmObject[list.size()]);
        })
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<RealmObject[]>() {


                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(RealmObject[] realmObject) {
                    if (observer != null) {
                        observer.onNext(realmObject);
                    }
                }

                @Override
                public void onError(Throwable t) {
                    if (observer != null) {
                        observer.onError(t);
                    }
                }

                @Override
                public void onComplete() {
                    if (observer != null) {
                        observer.onComplete();
                    }
                }
            });
    }

}
