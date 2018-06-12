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
        // 更新热门城市列表
        void updateCityList(List<CityBean> cityList);
        // 更新搜索结果的城市列表
        void updateResultCityList(String key, List<CityBean> cityList);
        // 跳转城市
        void gotoCity(CityBean cityBean, boolean isfresh);
    }
    public interface Presenter extends BasePresenter {
        // 添加城市
        void addCity(CityBean cityBean);
        // 搜索关键字
        void queryKey(String key);
    }

}
