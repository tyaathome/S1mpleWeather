package com.tyaathome.s1mpleweather.utils.tools;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyaathome on 2017/12/11.
 */

public class PermissionsTools {

    public static final int PERMISSON_REQUESTCODE = 0;

    public static boolean checkPermissions(Activity activity, String[] needPermissions) {
        if (Build.VERSION.SDK_INT >= 23
                && activity.getApplicationInfo().targetSdkVersion >= 23) {
            List<String> needRequestPermissonList = findDeniedPermissions(activity, needPermissions);
            if(needRequestPermissonList == null || needRequestPermissonList.size() == 0) {
                return true;
            }
            String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
            ActivityCompat.requestPermissions(activity, array, PERMISSON_REQUESTCODE);
            return false;
        }
        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions 权限
     * @return 获取权限集中需要申请权限的列表
     * @since 2.5.0
     */
    private static List<String> findDeniedPermissions(Activity activity, String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 23
                && activity.getApplicationInfo().targetSdkVersion >= 23) {
            for (String perm : permissions) {
                if (ContextCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.shouldShowRequestPermissionRationale(activity, perm)) {
                    needRequestPermissonList.add(perm);
                }
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否所有的权限都已经授权
     *
     * @param grantResults 权限
     * @return 检测是否所有的权限都已经授权
     * @since 2.5.0
     */
    public static boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    protected void startAppSettings(Activity activity) {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }

}
