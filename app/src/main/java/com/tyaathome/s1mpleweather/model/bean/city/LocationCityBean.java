package com.tyaathome.s1mpleweather.model.bean.city;

/**
 * Created by tyaathome on 2018/3/1.
 */

public class LocationCityBean {

    // 城市id
    private String id;
    private String parent_id;
    // 省
    private String province;
    // 市
    private String city;
    // 县
    private String county;
    // 街道名
    private String street;
    // 经度
    private double longitude;
    // 纬度
    private double latitude;

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

}
