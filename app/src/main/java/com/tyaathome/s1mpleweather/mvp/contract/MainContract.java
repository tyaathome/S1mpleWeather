package com.tyaathome.s1mpleweather.mvp.contract;

import android.graphics.drawable.Drawable;

import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;

public interface MainContract {

    interface View extends BaseView {
        void setBackground(Drawable drawable);
    }

    interface Presenter extends BasePresenter {
        void preperBackgroundData(String cityid);
    }

}
