package com.tyaathome.s1mpleweather.mvp.presenter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;

import com.tyaathome.s1mpleweather.model.bean.main.weekweather.WeekWeatherBean;
import com.tyaathome.s1mpleweather.model.bean.main.weekweather.WeekWeatherInfoBean;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;
import com.tyaathome.s1mpleweather.mvp.contract.MainContract;
import com.tyaathome.s1mpleweather.net.pack.main.week.WeekWeatherPackUp;
import com.tyaathome.s1mpleweather.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

public class MainPresenter implements MainContract.Presenter {

    private Context mContext;
    private List<RealmObject> preperData = new ArrayList<>();
    private ObservableEmitter<String> mEmitter;
    private MainContract.View mView;
    private static final String TAG = "MainPresenter";

    public MainPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void attachView(BaseView view) {
        mView = (MainContract.View) view;
    }

    @Override
    public void start() {
        initBackgroundObservable();
    }

    @Override
    public void preperBackgroundData(String cityid) {
        if(!TextUtils.isEmpty(cityid)) {
            if(mEmitter != null && !mEmitter.isDisposed()) {
                mEmitter.onNext(cityid);
            } else {
                initBackgroundObservable();
            }
        }
    }

    /**
     * 初始化更改背景的observable
     */
    private void initBackgroundObservable() {
        createBackgroundObservable().subscribe(observer);
    }

    /**
     * 创建更改背景的observable
     */
    private Observable<BitmapDrawable> createBackgroundObservable() {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> mEmitter = emitter)
                //.skip(1)
                .throttleWithTimeout(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .map(s -> {
                    WeekWeatherPackUp up = new WeekWeatherPackUp(s);
                    WeekWeatherBean bean = up.getCacheData(Realm.getDefaultInstance());
                    if(bean != null && bean.getWeek() != null && bean.getWeek().size() > 1) {
                        WeekWeatherInfoBean infoBean =  bean.getWeek().get(1);
                        if(infoBean != null && !TextUtils.isEmpty(infoBean.getWd_day_ico())) {
                            String ico = infoBean.getWd_day_ico();
                            return CommonUtils.getWeatherBackground(mContext, ico);
                        }
                    }
                    return null;
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observer<BitmapDrawable> observer = new Observer<BitmapDrawable>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(BitmapDrawable drawable) {
            String name = Thread.currentThread().getName();
            mView.setBackground(drawable);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };
}
