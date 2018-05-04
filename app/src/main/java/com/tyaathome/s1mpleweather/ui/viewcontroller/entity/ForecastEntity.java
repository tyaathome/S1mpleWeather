package com.tyaathome.s1mpleweather.ui.viewcontroller.entity;

import com.tyaathome.s1mpleweather.model.bean.main.weekweather.WeekWeatherBean;

public class ForecastEntity implements EntityImpl {

    private WeekWeatherBean weekWeatherBean;

    public ForecastEntity(WeekWeatherBean weekWeatherBean) {
        this.weekWeatherBean = weekWeatherBean;
    }

    public WeekWeatherBean getWeekWeatherBean() {
        return weekWeatherBean;
    }

    public void setWeekWeatherBean(WeekWeatherBean weekWeatherBean) {
        this.weekWeatherBean = weekWeatherBean;
    }
}
