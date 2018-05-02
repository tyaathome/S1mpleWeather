package com.tyaathome.s1mpleweather.mvp.contract;

import com.tyaathome.s1mpleweather.model.bean.main.sstq.SstqBean;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;

public interface CityContract {

    interface View extends BaseView {
        void fillSstqData(SstqBean bean);
    }

    interface Presenter extends BasePresenter {
        void getSstqData(String key);
    }

}
