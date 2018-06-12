package com.tyaathome.s1mpleweather.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.contract.CityContract;
import com.tyaathome.s1mpleweather.mvp.presenter.CityPresenter;
import com.tyaathome.s1mpleweather.ui.view.LoadMoreHeadView;
import com.tyaathome.s1mpleweather.ui.viewcontroller.ForecastViewController;
import com.tyaathome.s1mpleweather.ui.viewcontroller.MainViewController;
import com.tyaathome.s1mpleweather.ui.viewcontroller.entity.EntityImpl;
import com.tyaathome.s1mpleweather.ui.viewcontroller.entity.ForecastEntity;
import com.tyaathome.s1mpleweather.ui.viewcontroller.entity.MainEntity;
import com.tyaathome.s1mpleweather.utils.CommonUtils;

import java.util.List;
import java.util.Objects;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

@LayoutID(R.layout.fragment_city)
public class CityFragment extends BaseFragment implements CityContract.View {

    private CityPresenter presenter;
    private ViewGroup rootLayout;
    private MainViewController mainViewController;
    private ForecastViewController forecastViewController;
    public ScrollView scrollView;
    private PtrFrameLayout mPtrFrame;
    private String cityId = "";

    @Override
    protected BasePresenter onLoadPresenter() {
        presenter = new CityPresenter(this);
        return presenter;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        cityId = bundle.getString("key", "");
        rootLayout = findViewById(R.id.layout_root);
        initRefresh();
        Objects.requireNonNull(getView()).post(() -> {
            initViewController();
            //requestData();
            presenter.getData(cityId);
        });
    }

    @Override
    public void initEventAndData() {
    }

    private void initViewController() {
        // 屏幕高度
        int screenHeight = CommonUtils.getScreenHeight(getContext());
        // 主控件位置(y轴)
        int locationY = CommonUtils.getViewLocationY(getView());
        int height = screenHeight - locationY;
        if(height < 0) {
            height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        mainViewController = new MainViewController(getContext());
        mainViewController.attachRoot(rootLayout, params);

        forecastViewController = new ForecastViewController(getContext());
        forecastViewController.attachRoot(rootLayout);
    }

    private void initRefresh() {
        scrollView = findViewById(R.id.scrollview);
        mPtrFrame = findViewById(R.id.refresh);
        LoadMoreHeadView headView = new LoadMoreHeadView(getContext(), cityId);
        mPtrFrame.setHeaderView(headView);
        mPtrFrame.addPtrUIHandler(headView);
        //mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, scrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //requestData();
                presenter.getNetData(cityId);
            }
        });
    }

    /**
     * 请求数据
     */
    private void requestData() {
        presenter.getData(cityId);
    }

    @Override
    public void fillData(List<EntityImpl> entityList, String testMessage) {
        for(EntityImpl entity : entityList) {
            if(entity instanceof MainEntity) {
                MainEntity mainEntity = (MainEntity) entity;
                mainViewController.fillData(mainEntity);
                mainViewController.setText(testMessage);
            } else if (entity instanceof ForecastEntity) {
                ForecastEntity forecastEntity = (ForecastEntity) entity;
                forecastViewController.fillData(forecastEntity);
            }
        }
        mPtrFrame.refreshComplete();
    }

}
