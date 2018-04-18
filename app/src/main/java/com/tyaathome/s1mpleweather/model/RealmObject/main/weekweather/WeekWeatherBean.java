package com.tyaathome.s1mpleweather.model.RealmObject.main.weekweather;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WeekWeatherBean extends RealmObject {

    @PrimaryKey
    private String key;
    private String sys_time;
    private RealmList<WeekWeatherInfoBean> week;

    public String getSys_time() {
        return sys_time;
    }

    public void setSys_time(String sys_time) {
        this.sys_time = sys_time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public RealmList<WeekWeatherInfoBean> getWeek() {
        return week;
    }

    public void setWeek(RealmList<WeekWeatherInfoBean> week) {
        this.week = week;
    }
}
