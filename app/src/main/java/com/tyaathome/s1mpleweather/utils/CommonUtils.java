package com.tyaathome.s1mpleweather.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

    public static Object deepCopy(Object o) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try
        {
            ByteArrayOutputStream bos =
                    new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            // serialize and pass the object
            oos.writeObject(o);
            oos.flush();
            ByteArrayInputStream bin =
                    new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bin);
            // return the new object
            return ois.readObject();
        }
        catch(Exception e)
        {
            System.out.println("Exception in ObjectCloner = " + e);
        }
        finally
        {
            try {
                if(oos != null) {
                    oos.close();
                }
                if(ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取天气背景图片
     * @param context
     * @param s
     * @return
     */
    public static BitmapDrawable getWeatherBackground(Context context, String s) {
        String prefix = "bgs/";
        String name = "";
        String suffix = ".jpg";
        if (TextUtils.isEmpty(s)) {
            name = "00";
        } else {
            switch (s) {
                case "01":  // 多云
                    name = "01";
                    break;
                case "02":  // 阴
                    name = "02";
                    break;
                case "04":
                case "05":  // 雷阵雨
                    name = "04";
                    break;
                case "07":
                case "03":
                case "08":
                case "09":
                case "22":
                case "10":
                case "23":
                case "24":
                case "11":
                case "25":
                case "12":
                case "21":  // 雨
                    name = "07";
                    break;
                case "14":
                case "06":
                case "19":
                case "15":
                case "26":
                case "13":
                case "16":
                case "27":
                case "17":
                case "28":  // 雪
                    name = "14";
                    break;
                case "18":
                case "32":  // 雾霾
                    name = "18";
                    break;
                case "29":
                case "30":
                case "20":
                case "31":  // 沙
                    name = "29";
                    break;
                default:
                    name = "00";
                    break;
            }
        }
        String path = prefix + name + suffix;
        return getBitmapFromAssets(context, path);
    }

    /**
     * 从assets获取图片
     * @param context
     * @param filePath
     * @return
     */
    public static BitmapDrawable getBitmapFromAssets(Context context, String filePath) {
        BitmapDrawable bitmapDrawable = null;
        InputStream input = null;
        AssetManager assetManager = context.getAssets();
        try {
            input = assetManager.open(filePath);
            if(input != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmapDrawable;
    }


}
