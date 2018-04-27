package com.tyaathome.s1mpleweather.net.pack.main.week;

import com.tyaathome.s1mpleweather.net.pack.base.BasePackUp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 一周天气
 */
public class WeekWeatherPackUp extends BasePackUp {

    public static final String NAME = "p_new_week";
    public String id = "";

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
}
