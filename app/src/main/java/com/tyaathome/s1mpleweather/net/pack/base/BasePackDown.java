package com.tyaathome.s1mpleweather.net.pack.base;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * 响应
 * Created by tyaathome on 2016/4/18.
 */
public class BasePackDown<T extends RealmObject> {
    private T data;

    public void fillData(Realm realm, JSONObject json) {
        // 获取T.class
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        realm.beginTransaction();
        T bean = realm.createOrUpdateObjectFromJson(entityClass, json);
        if (bean == null && realm.isInTransaction()) {
            realm.cancelTransaction();
            return;
        }
        realm.commitTransaction();
        if (bean != null) {
            data = realm.copyFromRealm(bean);
        }
    }

    public T getData() {
        return data;
    }

}
