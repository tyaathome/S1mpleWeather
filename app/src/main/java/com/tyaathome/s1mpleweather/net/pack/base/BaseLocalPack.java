package com.tyaathome.s1mpleweather.net.pack.base;

/**
 * 本地数据包
 * Created by tyaathome on 2017/9/18.
 */

public abstract class BaseLocalPack extends BasePackDown {
    /**
     * 转成Json字符串
     *
     * @return
     */
    public abstract String toJsonStr();
}
