package com.tyaathome.s1mpleweather.utils;

import io.realm.Realm;
import io.realm.RealmModel;

public class RealmUtils {

    /**
     * 取消realm object 线程限制
     * @param object
     * @param <T>
     * @return
     */
    public static <T extends RealmModel> T unmanage(T object) {
        return Realm.getDefaultInstance().copyFromRealm(object);
    }

}
