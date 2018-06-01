package com.tyaathome.s1mpleweather.mvp.contract;

import android.graphics.drawable.Drawable;

import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;

import java.util.List;

public interface MainContract {

    interface View extends BaseView {
        void setBackground(Drawable drawable);
        void setCurrentCityName(String cityName);
        void setCityList(List<String> cityList);
    }

    interface Presenter extends BasePresenter {
        void selectPage(int position);
        void gotoSelectCity();
    }

}
