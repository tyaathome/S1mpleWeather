package com.tyaathome.s1mpleweather.utils.manager;

import android.text.TextUtils;

import com.tyaathome.s1mpleweather.net.pack.base.BasePackUp;
import com.tyaathome.s1mpleweather.net.pack.main.sstq.SstqPackUp;
import com.tyaathome.s1mpleweather.net.pack.main.week.WeekWeatherPackUp;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动下载数据管理类
 */
public class AutoDownloadManager {

    /**
     * 获取首页数据
     * @param key
     * @return
     */
    public static List<BasePackUp> getMainData(String key) {
        List<BasePackUp> mainDataList = new ArrayList<>();
        if(!TextUtils.isEmpty(key)) {
            // 一周天气
            WeekWeatherPackUp weekUp = new WeekWeatherPackUp();
            weekUp.id = key;
            mainDataList.add(weekUp);

            // 实时天气
            SstqPackUp sstqUp = new SstqPackUp();
            sstqUp.area = key;
            mainDataList.add(sstqUp);
        }
        return mainDataList;
    }

}
