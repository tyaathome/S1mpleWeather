package com.tyaathome.s1mpleweather.mvp.base;

import android.os.Bundle;

/**
 * Created by tyaathome on 2018/2/23.
 */

public interface BaseView {
    void initViews(Bundle savedInstanceState);
    void initEventAndData();
}
