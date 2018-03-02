package com.tyaathome.s1mpleweather.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * 公共工具类
 * Created by tyaathome on 2018/2/27.
 */

public class CommonUtils {

    @SuppressWarnings("SameParameterValue")
    public static InputStream getInputStreamFromAssets(Context context, String filepath) {
        try {
            return context.getAssets().open(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
