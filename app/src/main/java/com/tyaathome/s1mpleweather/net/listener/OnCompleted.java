package com.tyaathome.s1mpleweather.net.listener;


import com.tyaathome.s1mpleweather.net.pack.base.BasePackDown;

/**
 * Created by tyaathome on 2016/4/18.
 */
public interface OnCompleted {
    void onCompleted(BasePackDown response);
}
