package com.tyaathome.s1mpleweather.model.observable;

import com.tyaathome.s1mpleweather.net.pack.base.BasePackDown;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackUp;
import com.tyaathome.s1mpleweather.net.service.AppService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class DataObservable {

    private BasePackUp packUp;
    private List<BasePackUp> packUpList;
    public DataObservable(BasePackUp packUp) {
        this.packUp = packUp;
    }

    public DataObservable(List<BasePackUp> packUpList) {
        this.packUpList = packUpList;
    }

    public Observable<BasePackDown> getObservable() {
        return AppService.getInstance().getApi().getData(packUp);
    }

    public List<Observable<BasePackDown>> getObservableList() {
        List<Observable<BasePackDown>> observableList = new ArrayList<>();
        if(packUpList == null) {
            return observableList;
        }
        for(BasePackUp request : packUpList) {
            Observable<BasePackDown> observable = AppService.getInstance().getApi().getData(request);
            observableList.add(observable);
        }
        return observableList;
    }

}
