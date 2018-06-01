package com.tyaathome.s1mpleweather.mvp.base;

/**
 * presenter 父类
 * Created by tyaathome on 2018/2/23.
 */

public interface BasePresenter {
    <T extends BaseView> void attachView(T view);
    void start();
}
