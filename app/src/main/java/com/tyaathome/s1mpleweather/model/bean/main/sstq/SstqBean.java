package com.tyaathome.s1mpleweather.model.bean.main.sstq;

import com.tyaathome.s1mpleweather.model.bean.impl.DataHandlerImpl;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SstqBean extends RealmObject implements DataHandlerImpl<SstqBean> {

    @PrimaryKey
    private String key;
    private SstqInfoBean sstq;

    // Getter Methods

    public SstqInfoBean getSstq() {
        return sstq;
    }

    public String getKey() {
        return key;
    }

    // Setter Methods

    public void setSstq( SstqInfoBean sstq ) {
        this.sstq = sstq;
    }

    public void setKey( String key ) {
        this.key = key;
    }

    @Override
    public SstqBean getData() {
        return Realm.getDefaultInstance().where(SstqBean.class).equalTo("key", key).findFirst();
    }
}
