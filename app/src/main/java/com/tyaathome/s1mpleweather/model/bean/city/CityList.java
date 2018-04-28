package com.tyaathome.s1mpleweather.model.bean.city;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tyaathome on 2018/2/28.
 */

public class CityList extends RealmObject {

    @PrimaryKey
    private long key;
    private RealmList<CityRealmBean> cityList;

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public RealmList<CityRealmBean> getCityList() {
        return cityList;
    }

    public void setCityList(RealmList<CityRealmBean> cityList) {
        this.cityList = cityList;
    }
}
