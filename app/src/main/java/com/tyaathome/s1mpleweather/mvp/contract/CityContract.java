package com.tyaathome.s1mpleweather.mvp.contract;

import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;

public interface CityContract {

    interface View extends BaseView {
        void fillTextView();
    }

    interface Presenter extends BasePresenter {
    }

}
