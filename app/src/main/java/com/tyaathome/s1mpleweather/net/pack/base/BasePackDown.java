package com.tyaathome.s1mpleweather.net.pack.base;

import org.json.JSONObject;

import io.realm.Realm;

/**
 * 响应
 * Created by tyaathome on 2016/4/18.
 */
public abstract class BasePackDown<T> {
    abstract public void fillData(Realm realm,  JSONObject json);
    abstract  public T getData();
}
