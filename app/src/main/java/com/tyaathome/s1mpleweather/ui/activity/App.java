package com.tyaathome.s1mpleweather.ui.activity;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import io.realm.Realm;

/**
 * Created by tyaathome on 2018/2/24.
 */

public class App extends MultiDexApplication {

    public static Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    public static Realm getRealmInstance() {
        return realm;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
