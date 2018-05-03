package com.tyaathome.s1mpleweather.ui.fragment;

import android.os.Bundle;
import android.view.ViewGroup;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.contract.CityContract;
import com.tyaathome.s1mpleweather.mvp.presenter.CityPresenter;
import com.tyaathome.s1mpleweather.ui.viewcontroller.MainViewController;
import com.tyaathome.s1mpleweather.ui.viewcontroller.entity.EntityImpl;
import com.tyaathome.s1mpleweather.ui.viewcontroller.entity.MainEntity;
import com.tyaathome.s1mpleweather.utils.CommonUtils;

import java.util.List;

@LayoutID(R.layout.fragment_city)
public class CityFragment extends BaseFragment implements CityContract.View {

    private BasePresenter presenter;
    private ViewGroup rootLayout;

    private MainViewController mainViewController;

    @Override
    protected BasePresenter onLoadPresenter() {
        presenter = new CityPresenter(this);
        return presenter;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        rootLayout = findViewById(R.id.layout_root);
        initViewController();
    }

    @Override
    public void initEventAndData() {

    }

    private void initViewController() {
        int locaiontY = CommonUtils.getScreenHeight(getContext()) - CommonUtils.getViewLocationY(rootLayout);
        if(locaiontY < 0) {
            locaiontY = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, locaiontY);
        mainViewController = new MainViewController(getContext());
        mainViewController.attachRoot(rootLayout, params);
    }

    @Override
    public void fillData(List<EntityImpl> entityList) {
        for(EntityImpl entity : entityList) {
            if(entity instanceof MainEntity) {
                MainEntity mainEntity = (MainEntity) entity;
                mainViewController.fillData(mainEntity);
            }
        }
    }
}
