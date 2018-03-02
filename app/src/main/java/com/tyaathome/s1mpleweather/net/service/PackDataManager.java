package com.tyaathome.s1mpleweather.net.service;

import com.tyaathome.s1mpleweather.net.listener.OnCompleteWithDisposable;
import com.tyaathome.s1mpleweather.net.listener.OnCompleted;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackDown;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackUp;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by tyaathome on 2017/9/18.
 */

public class PackDataManager {

    /**
     * json包开头的p
     */
    private static String mP = "";

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
                        if(onComplete != null) {
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

//    public static void saveLocalPack(String key, BaseLocalPack pack) {
//        SQLiteDatabase db = null;
//        Context context = PcsInit.getInstance().getContext();
//        try {
//            db = DBHelper.getInstance(context).getReadableDatabase();
//            SqliteUtil.getInstance().setInfo(db, key, pack.toJsonStr());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (db != null && db.isOpen()) {
//                //db.close();
//            }
//        }
//    }
//
//    /**
//     * 删除本地数据
//     *
//     * @param key
//     */
//    public static void removeLocalData(String key) {
//        SqliteUtil.getInstance().deleteData(key);
//        //SqliteUtil.getInstance().closeDB();
//    }
//
//    public static BasePackDown getNetPack(String key) {
//        if (TextUtils.isEmpty(key)) {
//            return null;
//        }
//        String json = SqliteUtil.getInstance().getInfo(key);
//        if (!TextUtils.isEmpty(json)) {
//            BasePackDown BasePackDown = NetFactory.getResponse(key);
//            if (BasePackDown == null) {
//                return null;
//            }
//            BasePackDown.fillData(json);
//            return BasePackDown;
//        }
//        return null;
//    }
//
//    public static BaseLocalPack getLocalPack(String key) {
//        String json = SqliteUtil.getInstance().getInfo(key);
//        if (!TextUtils.isEmpty(json)) {
//            BaseLocalPack pack = NetFactory.getLocalPack(key);
//            if (pack == null) {
//                return null;
//            }
//            pack.fillData(json);
//            return pack;
//        }
//        return null;
//    }

    /**
     * 设置head里的p
     *
     * @param p
     */
    public static void setP(String p) {
        mP = p;
    }

    public static String getP() {
        return mP;
    }

}
