package com.tyaathome.s1mpleweather.net.pack.main.week;

import android.text.TextUtils;

import com.tyaathome.s1mpleweather.model.bean.main.weekweather.WeekWeatherBean;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackDown;

import org.json.JSONObject;

import io.realm.Realm;

/**
 * 一周天气
 */
public class WeekWeatherPackDown extends BasePackDown {

    public WeekWeatherBean data;

    @Override
    public void fillData(Realm realm, final JSONObject json) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(final Realm realm) {
                realm.createOrUpdateObjectFromJson(WeekWeatherBean.class, json);
                String key = json.optString("key");
                if(!TextUtils.isEmpty(key)) {
                    data = realm.where(WeekWeatherBean.class).equalTo("key", key).findFirst();
                }
            }
        });
    }
}
