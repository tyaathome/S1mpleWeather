package com.tyaathome.s1mpleweather.ui.provider.columns;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * 城市对象字段
 * Created by tyaathome on 2018/2/27.
 */

public class CityColumns implements BaseColumns {

    public static final Uri CONTENT_URI = Uri.parse("content://com.tyaathome.s1mpleweather.CityContentProvider");
    public static final String ID = "ID";
    public static final String PARENT_ID = "PARENT_ID";
    public static final String PROVINCE = "PROVINCE";
    public static final String CITY = "CITY";
    public static final String COUNTY = "COUNTY";
    public static final String PINYIN = "PINYIN";
    public static final String PINYIN_SIMPLE = "PINYIN_SIMPLE";

}
