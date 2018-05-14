package com.tyaathome.s1mpleweather.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.tyaathome.s1mpleweather.model.bean.main.sstq.SstqBean;
import com.tyaathome.s1mpleweather.model.bean.main.weekweather.WeekWeatherBean;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;
import com.tyaathome.s1mpleweather.mvp.contract.CityContract;
import com.tyaathome.s1mpleweather.net.listener.MyObserver;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackUp;
import com.tyaathome.s1mpleweather.net.service.PackDataManager;
import com.tyaathome.s1mpleweather.ui.viewcontroller.entity.EntityImpl;
import com.tyaathome.s1mpleweather.ui.viewcontroller.entity.ForecastEntity;
import com.tyaathome.s1mpleweather.ui.viewcontroller.entity.MainEntity;
import com.tyaathome.s1mpleweather.utils.manager.AutoDownloadManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

public class CityPresenter implements CityContract.Presenter {

    private Context context;
    private Fragment currentFragment;
    private CityContract.View view;
    private SstqBean sstqBean;
    private WeekWeatherBean weekWeatherBean;

    public CityPresenter(Fragment fragment) {
        this.context = fragment.getContext();
        currentFragment = fragment;
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (CityContract.View) view;
    }

    @Override
    public void start() {
        if(currentFragment != null) {
            Bundle bundle = currentFragment.getArguments();
            String key = bundle.getString("key", "");
            if(!TextUtils.isEmpty(key)) {
                getData(key);
            }
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void getData(String key) {
        if(TextUtils.isEmpty(key)) {
           return;
        }
        List<BasePackUp> packList = AutoDownloadManager.getMainData(key);
        // 合并请求缓存数据
        PackDataManager.zipCache(packList, observerCache);
        // 合并请求网络数据
        PackDataManager.zipRequest(packList, observerNet);


    }

    private MyObserver<RealmObject[]> observerCache = new MyObserver<RealmObject[]>() {
        @Override
        public void onNext(RealmObject[] realmObject) {
            for(RealmObject bean : realmObject) {
                if (bean instanceof SstqBean) {
                    sstqBean = (SstqBean) bean;
                } else if (bean instanceof WeekWeatherBean) {
                    weekWeatherBean = (WeekWeatherBean) bean;
                }
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            // 首页组件数据填充
            List<EntityImpl> entityList = new ArrayList<>();
            // 第一屏数据
            MainEntity mainEntity = new MainEntity(sstqBean, weekWeatherBean);
            entityList.add(mainEntity);
            // 预报数据
            ForecastEntity forecastEntity = new ForecastEntity(weekWeatherBean);
            entityList.add(forecastEntity);
            view.fillData(entityList, "cache");
        }
    };

    private MyObserver<RealmObject[]> observerNet = new MyObserver<RealmObject[]>() {
        @Override
        public void onNext(RealmObject[] realmObject) {
            for(RealmObject bean : realmObject) {
                if (bean instanceof SstqBean) {
                    sstqBean = (SstqBean) bean;
                } else if (bean instanceof WeekWeatherBean) {
                    weekWeatherBean = (WeekWeatherBean) bean;
                }
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            // 首页组件数据填充
            List<EntityImpl> entityList = new ArrayList<>();
            // 第一屏数据
            MainEntity mainEntity = new MainEntity(sstqBean, weekWeatherBean);
            entityList.add(mainEntity);
//            // 预报数据
            ForecastEntity forecastEntity = new ForecastEntity(weekWeatherBean);
            entityList.add(forecastEntity);
            view.fillData(entityList, "net");
        }
    };


}
