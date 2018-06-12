package com.tyaathome.s1mpleweather.mvp.contract;

import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;
import com.tyaathome.s1mpleweather.ui.viewcontroller.entity.EntityImpl;

import java.util.List;

public interface CityContract {

    interface View extends BaseView {
        void fillData(List<EntityImpl> entityList, String testMessage);
    }

    interface Presenter extends BasePresenter {
        // 智能获取数据(缓存或网络)
        void getData(String key);
        // 获取网络数据
        void getNetData(String key);
    }

}
