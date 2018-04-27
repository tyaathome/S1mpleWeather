package com.tyaathome.s1mpleweather.net.factory;

import android.text.TextUtils;

import com.tyaathome.s1mpleweather.net.pack.base.BasePackDown;
import com.tyaathome.s1mpleweather.net.pack.main.sstq.SstqPackDown;
import com.tyaathome.s1mpleweather.net.pack.main.sstq.SstqPackUp;
import com.tyaathome.s1mpleweather.net.pack.main.week.WeekWeatherPackDown;
import com.tyaathome.s1mpleweather.net.pack.main.week.WeekWeatherPackUp;


/**
 * 网络包工厂
 * Created by tyaathome on 2016/4/18.
 */
public class NetFactory {
    public static BasePackDown getResponse(String keytemp) {
        if (TextUtils.isEmpty(keytemp)) {
            return null;
        }

        String[] keyList = keytemp.split("#");
        if (keyList.length == 0) {
            return null;
        }
        String key = keyList[0];

        if (key.equals(WeekWeatherPackUp.NAME)) {
            // 一周天气
            return new WeekWeatherPackDown();
        } else if (key.equals(SstqPackUp.NAME)) {
            // 实时天气
            return new SstqPackDown();
        }

        return null;
    }

//    public static BaseLocalPack getLocalPack(String key) {
//        if (TextUtils.isEmpty(key)) {
//            return null;
//        }
//        if (key.equals(LocationSetLocalPack.KEY)) {
//            return new LocationSetLocalPack();
//        } else if (key.equals(InitLocalPack.KEY)) {
//            //初始化配置
//            return new InitLocalPack();
//        } else if (key.equals(UrlLocalPack.KEY)) {
//            //URL地址
//            return new UrlLocalPack();
//        } else if (key.equals(CityMainLocalPack.KEY)) {
//            //首页显示的城市信息
//            return new CityMainLocalPack();
//        } else if (key.equals(CityLocationLocalPack.KEY)) {
//            //定位到的城市信息
//            return new CityLocationLocalPack();
//        } else if (key.equals(CityInfoLocalPack.KEY)) {
//            //已选城市城市
//            return new CityInfoLocalPack();
//        } else if (key.equals(TestLocationLocalPack.KEY)) {
//            // 测试定位
//            return new TestLocationLocalPack();
//        }
//        return null;
//    }
}
