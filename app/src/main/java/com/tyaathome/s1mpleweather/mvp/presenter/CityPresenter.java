package com.tyaathome.s1mpleweather.mvp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.tyaathome.s1mpleweather.model.bean.main.sstq.SstqBean;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;
import com.tyaathome.s1mpleweather.mvp.contract.CityContract;
import com.tyaathome.s1mpleweather.net.listener.OnCompleted;
import com.tyaathome.s1mpleweather.net.pack.main.sstq.SstqPackDown;
import com.tyaathome.s1mpleweather.net.pack.main.sstq.SstqPackUp;
import com.tyaathome.s1mpleweather.net.service.PackDataManager;

public class CityPresenter implements CityContract.Presenter {

    private Context context;
    private Fragment currentFragment;
    private CityContract.View view;

    public CityPresenter(Fragment fragment) {
        this.context = fragment.getContext();
        currentFragment = fragment;
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (CityContract.View) view;
    }

    @Override
    public void start() {
        if(currentFragment != null) {
            Bundle bundle = currentFragment.getArguments();
            String key = bundle.getString("key", "");
            if(!TextUtils.isEmpty(key)) {
                getSstqData(key);
            }
        }
    }

    @Override
    public void getSstqData(String key) {
        if(TextUtils.isEmpty(key)) {
           return;
        }
        SstqPackUp sstqPackUp = new SstqPackUp(key);

        PackDataManager.startRequest(sstqPackUp, (OnCompleted<SstqPackDown>) response -> {
            SstqBean bean = response.getData();
            view.fillSstqData(bean);
        });
        SstqBean sstqBean = sstqPackUp.getCacheData();
        view.fillSstqData(sstqBean);
    }
}
