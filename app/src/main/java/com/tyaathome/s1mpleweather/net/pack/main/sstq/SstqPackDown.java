package com.tyaathome.s1mpleweather.net.pack.main.sstq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        Gson gson = new GsonBuilder().create();
        data = gson.fromJson(json.toString(), SstqBean.class);
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(data));
    }

    @Override
    public SstqBean getData() {
        return data;
    }
}
