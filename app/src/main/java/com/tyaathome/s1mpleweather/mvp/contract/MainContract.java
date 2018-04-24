package com.tyaathome.s1mpleweather.mvp.contract;

import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;

public interface MainContract {

    interface View extends BaseView {
        void update();
    }

    interface Presenter extends BasePresenter {

    }

}
