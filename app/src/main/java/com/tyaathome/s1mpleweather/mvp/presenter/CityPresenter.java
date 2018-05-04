package com.tyaathome.s1mpleweather.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.tyaathome.s1mpleweather.model.bean.main.sstq.SstqBean;
import com.tyaathome.s1mpleweather.model.bean.main.weekweather.WeekWeatherBean;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;
import com.tyaathome.s1mpleweather.mvp.contract.CityContract;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackUp;
import com.tyaathome.s1mpleweather.net.pack.main.sstq.SstqPackUp;
import com.tyaathome.s1mpleweather.net.pack.main.week.WeekWeatherPackUp;
import com.tyaathome.s1mpleweather.net.service.PackDataManager;
import com.tyaathome.s1mpleweather.ui.viewcontroller.entity.EntityImpl;
import com.tyaathome.s1mpleweather.ui.viewcontroller.entity.ForecastEntity;
import com.tyaathome.s1mpleweather.ui.viewcontroller.entity.MainEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
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

    @Override
    public void getData(String key) {
        if(TextUtils.isEmpty(key)) {
           return;
        }
        SstqPackUp sstqPackUp = new SstqPackUp(key);
        WeekWeatherPackUp weekWeatherPackUp = new WeekWeatherPackUp(key);
        List<BasePackUp> packList = new ArrayList<>();
        packList.add(sstqPackUp);
        packList.add(weekWeatherPackUp);

        // 合并请求缓存数据
        PackDataManager.mergeCache(packList, requestObserver);
        // 合并请求网络数据
        PackDataManager.mergeRequest(packList, requestObserver);
    }

    private Observer<RealmObject> requestObserver = new Observer<RealmObject>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(RealmObject realmObject) {
            if(realmObject instanceof SstqBean) {
                sstqBean = (SstqBean) realmObject;
            } else if (realmObject instanceof WeekWeatherBean) {
                weekWeatherBean = (WeekWeatherBean) realmObject;
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
            view.fillData(entityList);
        }
    };


}
