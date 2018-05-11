package com.tyaathome.s1mpleweather.net.pack.main.sstq;

import com.tyaathome.s1mpleweather.model.bean.main.sstq.SstqBean;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackUp;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

/**
 * 实时天气
 */
public class SstqPackUp extends BasePackUp<SstqBean> {

    private String area;
    public static final String NAME = "sstq";

    public SstqPackUp(String area) {
        this.area = area;
    }

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

    @Override
    protected SstqBean queryData(Realm realm) {
        return realm.where(SstqBean.class).equalTo("key", area).findFirst();
    }

}
