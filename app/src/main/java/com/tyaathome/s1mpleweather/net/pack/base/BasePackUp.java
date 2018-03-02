package com.tyaathome.s1mpleweather.net.pack.base;

import org.json.JSONObject;

/**
 * 请求
 * Created by tyaathome on 2016/4/18.
 */
public abstract class BasePackUp {
    abstract public JSONObject toJSON();
    abstract public String getName();
}
