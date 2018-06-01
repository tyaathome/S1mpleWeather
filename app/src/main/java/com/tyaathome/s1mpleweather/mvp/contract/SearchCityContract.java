package com.tyaathome.s1mpleweather.mvp.contract;

import com.tyaathome.s1mpleweather.model.bean.city.CityBean;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;

import java.util.List;

/**
 * Created by tyaathome on 2018/05/31.
 */
public class SearchCityContract {

    public interface View extends BaseView {
        void updateCityList(List<CityBean> cityList);
    }
    public interface Presenter extends BasePresenter {
        void addCity(CityBean cityBean);
    }

}
