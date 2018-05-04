package com.tyaathome.s1mpleweather.ui.viewcontroller;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;
import com.tyaathome.s1mpleweather.model.bean.main.weekweather.WeekWeatherBean;
import com.tyaathome.s1mpleweather.model.bean.main.weekweather.WeekWeatherInfoBean;
import com.tyaathome.s1mpleweather.ui.viewcontroller.entity.ForecastEntity;

@LayoutID(R.layout.layout_controller_forecast)
public class ForecastViewController extends ViewController<ForecastEntity> {

    private TextView tvInfo;

    public ForecastViewController(Context context) {
        super(context);
    }

    @Override
    protected void onCreatedView(View view) {
        tvInfo = findViewById(R.id.tv_info);
    }

    @Override
    protected void onBindView(ForecastEntity data) {
        if(data == null) return;
        WeekWeatherBean weekWeatherBean = data.getWeekWeatherBean();
        StringBuilder str = new StringBuilder();
        for(WeekWeatherInfoBean bean : weekWeatherBean.getWeek()) {
            str.append(bean.getDay())
                    .append(", ")
                    .append(bean.getGdt())
                    .append(", ")
                    .append(bean.getHigt())
                    .append(", ")
                    .append(bean.getIs_night())
                    .append(", ")
                    .append(bean.getLowt())
                    .append(", ")
                    .append(bean.getSunrise_time())
                    .append(", ")
                    .append(bean.getSunset_time())
                    .append(", ")
                    .append(bean.getWind_dir_day())
                    .append("\n");
        }
        tvInfo.setText(str.toString());
    }















}
