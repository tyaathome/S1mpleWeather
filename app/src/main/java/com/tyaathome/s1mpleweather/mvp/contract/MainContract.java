package com.tyaathome.s1mpleweather.mvp.contract;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.tyaathome.s1mpleweather.model.bean.city.CityBean;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;

import java.util.List;

public interface MainContract {

    interface View extends BaseView {
        void setBackground(Drawable drawable);
        void setCurrentCityName(String cityName);
        void setCityList(List<String> cityList);
        void selectResult(Intent intent);
        void setPagerCurrentItem(int position);
    }

    interface Presenter extends BasePresenter {
        void selectPage(int position);
        void gotoSelectCity();
        // 刷新城市列表选项卡
        void updateCityList(CityBean cityBean);
    }

}
