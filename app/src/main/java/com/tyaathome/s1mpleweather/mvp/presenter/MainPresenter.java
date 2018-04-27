package com.tyaathome.s1mpleweather.mvp.presenter;

import android.content.Context;

import com.tyaathome.s1mpleweather.mvp.base.BaseView;
import com.tyaathome.s1mpleweather.mvp.contract.MainContract;

public class MainPresenter implements MainContract.Presenter {

    private Context mContext;

    public MainPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void attachView(BaseView view) {

    }

    @Override
    public void start() {

    }

    private void request() {
//        WeekWeatherPackUp up = new WeekWeatherPackUp();
//        up.area = "30900";
//        up.country = "32530";
//        PackDataManager.startRequest(up, new OnCompleted() {
//            @Override
//            public void onCompleted(BasePackDown response) {
//                if(response instanceof WeekWeatherPackDown) {
//                    WeekWeatherPackDown down = (WeekWeatherPackDown) response;
//                }
//            }
//        });
    }
}
