package com.tyaathome.s1mpleweather.net.pack.main.week;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tyaathome.s1mpleweather.model.bean.main.weekweather.WeekWeatherBean;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackDown;

import org.json.JSONObject;

import io.realm.Realm;

/**
 * 一周天气
 */
public class WeekWeatherPackDown extends BasePackDown<WeekWeatherBean> {

    private WeekWeatherBean data;

    @Override
    public void fillData(Realm realm, final JSONObject json) {
        Gson gson = new GsonBuilder().create();
        data = gson.fromJson(json.toString(), WeekWeatherBean.class);
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(data));
    }

    @Override
    public WeekWeatherBean getData() {
        return data;
    }
}
