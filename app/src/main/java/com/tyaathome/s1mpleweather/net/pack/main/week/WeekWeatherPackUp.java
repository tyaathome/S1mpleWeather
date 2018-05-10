package com.tyaathome.s1mpleweather.net.pack.main.week;

import com.tyaathome.s1mpleweather.model.bean.main.weekweather.WeekWeatherBean;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackUp;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

/**
 * 一周天气
 */
public class WeekWeatherPackUp extends BasePackUp<WeekWeatherBean> {

    public static final String NAME = "p_new_week";
    private String id;

    public WeekWeatherPackUp(String id) {
        this.id = id;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("area", id);
            object.put("country", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public WeekWeatherBean getCacheData() {
        return Realm.getDefaultInstance().where(WeekWeatherBean.class).equalTo("key", id).findFirst();
    }

}
