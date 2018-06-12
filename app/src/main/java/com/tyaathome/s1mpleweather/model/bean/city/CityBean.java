package com.tyaathome.s1mpleweather.model.bean.city;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by tyaathome on 2018/2/28.
 */

public class CityBean extends RealmObject implements Serializable {
    // 城市id
    private String id = "";
    // 父id
    private String parent_id = "";
    // 省
    private String province = "";
    // 市
    private String city = "";
    // 县
    private String county = "";
    // 全拼
    private String pinyin = "";
    // 简拼
    private String pinyin_simple = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPinyin_simple() {
        return pinyin_simple;
    }

    public void setPinyin_simple(String pinyin_simple) {
        this.pinyin_simple = pinyin_simple;
    }

}
