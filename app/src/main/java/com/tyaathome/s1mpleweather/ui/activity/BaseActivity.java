package com.tyaathome.s1mpleweather.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;
import com.tyaathome.s1mpleweather.utils.manager.InjectManager;

/**
 * activity 父类
 * Created by tyaathome on 2018/2/24.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected P mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注入layout
        InjectManager.inject(this);
        mPresenter = onLoadPresenter();
        if(getPresenter() != null) {
            getPresenter().attachView(this);
            initViews(savedInstanceState);
            initEventAndData();
            getPresenter().start();
        }
    }

    public P getPresenter() {
        return mPresenter;
    }

    protected abstract P onLoadPresenter();
}