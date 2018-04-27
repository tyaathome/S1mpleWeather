package com.tyaathome.s1mpleweather.model.RealmObject.main.sstq;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SstqBean extends RealmObject {

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

}
