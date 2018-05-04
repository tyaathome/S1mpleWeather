package com.tyaathome.s1mpleweather.ui.activity.loading;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.contract.LoadingContract;
import com.tyaathome.s1mpleweather.mvp.presenter.LoadingPresenter;
import com.tyaathome.s1mpleweather.ui.activity.BaseActivity;
import com.tyaathome.s1mpleweather.ui.activity.MainActivity;
import com.tyaathome.s1mpleweather.utils.tools.CityTools;
import com.tyaathome.s1mpleweather.utils.tools.PermissionsTools;

@LayoutID(R.layout.activity_loading)
public class LoadingActivity extends BaseActivity implements LoadingContract.View {

    private String[] needPermissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private LoadingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BasePresenter onLoadPresenter() {
        presenter = new LoadingPresenter(this);
        return presenter;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initEventAndData() {
        checkPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionsTools.PERMISSON_REQUESTCODE) {
            if (PermissionsTools.verifyPermissions(grantResults)) {
                initApp();
            }
        }
    }

    @Override
    public void gotoNextActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    /**
     * 检查权限
     */
    private void checkPermissions() {
        if (PermissionsTools.checkPermissions(this, needPermissions)) {
            initApp();
        }
    }

    private void initApp() {
        initCityList();
        if(presenter != null) {
            presenter.beginLocation();
        }
    }

    /**
     * 初始化城市列表
     */
    private void initCityList() {
        long start = System.currentTimeMillis();
        CityTools.getInstance(this);
    }
}
