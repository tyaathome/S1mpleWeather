package com.tyaathome.s1mpleweather.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.net.listener.OnCompleted;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackDown;
import com.tyaathome.s1mpleweather.net.pack.week.WeekWeatherPackDown;
import com.tyaathome.s1mpleweather.net.pack.week.WeekWeatherPackUp;
import com.tyaathome.s1mpleweather.net.service.PackDataManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WeekWeatherPackUp up = new WeekWeatherPackUp();
        up.area = "30900";
        up.country = "32530";
        PackDataManager.startRequest(up, new OnCompleted() {
            @Override
            public void onCompleted(BasePackDown response) {
                if(response instanceof WeekWeatherPackDown) {
                    WeekWeatherPackDown down = (WeekWeatherPackDown) response;
                }
            }
        });
    }
}
