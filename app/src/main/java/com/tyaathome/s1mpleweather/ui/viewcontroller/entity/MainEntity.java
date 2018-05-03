package com.tyaathome.s1mpleweather.ui.viewcontroller.entity;

import com.tyaathome.s1mpleweather.model.bean.main.sstq.SstqBean;
import com.tyaathome.s1mpleweather.model.bean.main.weekweather.WeekWeatherBean;

public class MainEntity implements EntityImpl {
    private SstqBean sstqBean;
    private WeekWeatherBean weekWeatherBean;

    public MainEntity(SstqBean sstqBean, WeekWeatherBean weekWeatherBean) {
        this.sstqBean = sstqBean;
        this.weekWeatherBean = weekWeatherBean;
    }

    public void setSstqBean(SstqBean sstqBean) {
        this.sstqBean = sstqBean;
    }

    public SstqBean getSstqBean() {
        return sstqBean;
    }

    public WeekWeatherBean getWeekWeatherBean() {
        return weekWeatherBean;
    }

    public void setWeekWeatherBean(WeekWeatherBean weekWeatherBean) {
        this.weekWeatherBean = weekWeatherBean;
    }
}
