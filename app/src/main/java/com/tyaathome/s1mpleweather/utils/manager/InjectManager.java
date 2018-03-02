package com.tyaathome.s1mpleweather.utils.manager;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;


/**
 * 注解注入管理类
 * Created by tyaathome on 2017/9/19.
 */

public class InjectManager {

    /**
     * 注入activity
     * @param activity
     */
    public static void inject(Activity activity) {
        Class<? extends Activity> target = activity.getClass();
        LayoutID layoutID = target.getAnnotation(LayoutID.class);
        if(layoutID != null) {
            activity.setContentView(layoutID.value());
        }
    }

    /**
     * 注入fragment
     * @param fragment
     * @param container
     * @return
     */
    public static View inject(Fragment fragment, @Nullable ViewGroup container) {
        Class<? extends Fragment> target = fragment.getClass();
        LayoutID layoutID = target.getAnnotation(LayoutID.class);
        if(layoutID != null) {
            return LayoutInflater.from(fragment.getContext()).inflate(layoutID.value(), container, false);
        }
        return null;
    }

}
