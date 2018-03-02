package com.tyaathome.s1mpleweather.model.bean.city;

/**
 * Created by tyaathome on 2018/3/1.
 */

public class CityBean {

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
}
