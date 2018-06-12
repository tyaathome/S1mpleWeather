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
        // 设置背景图片时是否延迟
        void selectPage(int position, boolean isDelay);
        void gotoSelectCity();
        // 刷新城市列表选项卡
        void updateCityList(CityBean cityBean);
    }

}
