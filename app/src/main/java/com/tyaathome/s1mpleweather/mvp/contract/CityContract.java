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
        void getData(String key);
    }

}
