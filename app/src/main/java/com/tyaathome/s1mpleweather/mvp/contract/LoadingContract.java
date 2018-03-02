package com.tyaathome.s1mpleweather.mvp.contract;

import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;

/**
 * Created by tyaathome on 2018/2/24.
 */

public interface LoadingContract {
    interface View extends BaseView {
        void gotoNextActivity();
    }

    interface Presenter extends BasePresenter {
        void beginLocation();
    }

}
