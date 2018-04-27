package com.tyaathome.s1mpleweather.net.pack.main.sstq;

import com.tyaathome.s1mpleweather.net.pack.base.BasePackUp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 实时天气
 */
public class SstqPackUp extends BasePackUp {

    public String area = "";
    public static final String NAME = "sstq";

    @Override
    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("area", area);
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
