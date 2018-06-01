package com.tyaathome.s1mpleweather.ui.activity.city;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;
import com.tyaathome.s1mpleweather.model.bean.city.CityBean;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.contract.SearchCityContract;
import com.tyaathome.s1mpleweather.mvp.presenter.SearchCityPresenter;
import com.tyaathome.s1mpleweather.ui.activity.BaseActivity;
import com.tyaathome.s1mpleweather.ui.adapter.city.PopularCityAdapter;
import com.tyaathome.s1mpleweather.ui.adapter.decoration.SpaceItemDecoration;
import com.tyaathome.s1mpleweather.utils.CommonUtils;

import java.util.List;

/**
 * Created by tyaathome on 2018/05/31.
 * 搜索城市
 */
@LayoutID(R.layout.activity_search_city)
public class SearchCityActivity extends BaseActivity implements SearchCityContract.View {

    private SearchCityPresenter mPresenter;
    private RecyclerView rvPopularCity;
    private PopularCityAdapter popularCityAdapter;

    @Override
    protected BasePresenter onLoadPresenter() {
        mPresenter = new SearchCityPresenter(this);
        return mPresenter;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initPopularCityList();
    }

    @SuppressLint("CheckResult")
    @Override
    public void initEventAndData() {
        popularCityAdapter.getPositionClicks().subscribe(cityBean -> {
            mPresenter.addCity(cityBean);
        });
    }

    private void initPopularCityList() {
        rvPopularCity = findViewById(R.id.rv_popular_city);
        popularCityAdapter = new PopularCityAdapter(this);
        rvPopularCity.setAdapter(popularCityAdapter);
        rvPopularCity.setLayoutManager(new GridLayoutManager(this, 3));
        int column = (int) CommonUtils.dp2px(this, 20);
        int row = (int) CommonUtils.dp2px(this, 20);
        rvPopularCity.addItemDecoration(new SpaceItemDecoration(column, row, 3));
    }

    @Override
    public void updateCityList(List<CityBean> cityList) {
        popularCityAdapter.updateSelectedCityList(cityList);
    }
}
