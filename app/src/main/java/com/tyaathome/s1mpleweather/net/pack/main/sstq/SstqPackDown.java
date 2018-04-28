package com.tyaathome.s1mpleweather.net.pack.main.sstq;

import android.text.TextUtils;

import com.tyaathome.s1mpleweather.model.bean.main.sstq.SstqBean;
import com.tyaathome.s1mpleweather.model.bean.main.sstq.SstqInfoBean;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackDown;

import org.json.JSONObject;

import io.realm.Realm;

/**
 * 实时天气
 */
public class SstqPackDown extends BasePackDown {

    public SstqBean data;
    private SstqInfoBean bean;

    @Override
    public void fillData(Realm realm, final JSONObject json) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateObjectFromJson(SstqBean.class, json);
                String key = json.optString("key");
                if(!TextUtils.isEmpty(key)) {
                    data = realm.where(SstqBean.class).equalTo("key", key).findFirst();
                    bean = data.getSstq();
                }
            }
        });
    }
}
