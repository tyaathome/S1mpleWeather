package com.tyaathome.s1mpleweather.net.pack.main.sstq;

import android.text.TextUtils;

import com.tyaathome.s1mpleweather.model.bean.main.sstq.SstqBean;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackDown;

import org.json.JSONObject;

import io.realm.Realm;

/**
 * 实时天气
 */
public class SstqPackDown extends BasePackDown<SstqBean> {

    private SstqBean data;

    @Override
    public void fillData(Realm realm, final JSONObject json) {
        realm.executeTransaction(realm1 -> {
            realm1.createOrUpdateObjectFromJson(SstqBean.class, json);
            String key = json.optString("key");
            if(!TextUtils.isEmpty(key)) {
                data = realm1.where(SstqBean.class).equalTo("key", key).findFirst();
            }
        });
    }

    @Override
    public SstqBean getData() {
        return data;
    }
}
