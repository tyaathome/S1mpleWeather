package com.tyaathome.s1mpleweather.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.WindowManager;

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

    /**
     * z: 获取屏幕的高和宽
     *

     * @return 屏幕的高
     */
    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context c) {
        WindowManager windowManager = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getHeight();// 获得高度，获得宽度也类似
    }

    /**
     * z: 获取屏幕的高和宽
     *
     * @return 屏幕的宽
     */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context c) {
        WindowManager windowManager = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();// 获得宽度，获得高度也类似
    }

    /**
     * 获取控件位置
     * @param view
     * @return
     */
    public static int[] getViewLocation(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    /**
     * 获取控件位置X坐标
     * @param view
     * @return
     */
    public static int getViewLocationX(View view) {
        int[] location = getViewLocation(view);
        if(location.length == 2) {
            return location[0];
        }
        return 0;
    }

    /**
     * 获取控件位置Y坐标
     * @param view
     * @return
     */
    public static int getViewLocationY(View view) {
        int[] location = getViewLocation(view);
        if(location.length == 2) {
            return location[1];
        }
        return 0;
    }

    /**
     * 获取底部按键高度
     * @param context
     * @return
     */
    public static int getBottomButtonHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}
