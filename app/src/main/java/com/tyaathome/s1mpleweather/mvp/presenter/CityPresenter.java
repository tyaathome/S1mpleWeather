package com.tyaathome.s1mpleweather.mvp.presenter;

import android.content.Context;

import com.tyaathome.s1mpleweather.mvp.base.BaseView;
import com.tyaathome.s1mpleweather.mvp.contract.CityContract;

public class CityPresenter implements CityContract.Presenter {

    private Context context;
    private CityContract.View view;

    public CityPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (CityContract.View) view;
    }

    @Override
    public void start() {
        view.fillTextView();
    }

}
