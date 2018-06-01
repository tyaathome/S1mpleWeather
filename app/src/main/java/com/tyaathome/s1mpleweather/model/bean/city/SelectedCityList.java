package com.tyaathome.s1mpleweather.model.bean.city;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tyaathome on 2018/05/31.
 * 已选择的城市列表
 */
public class SelectedCityList extends RealmObject {

    @PrimaryKey
    private long key;
    private RealmList<CityBean> cityList;

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public RealmList<CityBean> getCityList() {
        return cityList;
    }

    public void setCityList(RealmList<CityBean> cityList) {
        this.cityList = cityList;
    }
}
