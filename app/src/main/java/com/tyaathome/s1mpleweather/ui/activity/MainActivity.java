package com.tyaathome.s1mpleweather.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.contract.MainContract;
import com.tyaathome.s1mpleweather.mvp.presenter.MainPresenter;
import com.tyaathome.s1mpleweather.ui.adapter.city.CityFragmentAdapter;

import java.util.List;

@LayoutID(R.layout.activity_main)
public class MainActivity extends BaseActivity implements MainContract.View {

    private MainPresenter presenter;
    private ViewPager viewPager;
    private CityFragmentAdapter adapter;
    private TextView tvCityName;
    private ViewGroup layoutRoot;
    private int count = 5;

    @Override
    protected BasePresenter onLoadPresenter() {
        presenter = new MainPresenter(this);
        return presenter;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        layoutRoot = findViewById(R.id.layout_root);
        viewPager = findViewById(R.id.viewPager);
        tvCityName = findViewById(R.id.tv_city_name);
    }

    @Override
    public void initEventAndData() {
        adapter = new CityFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        //viewPager.setOffscreenPageLimit(9);
        tvCityName.setOnClickListener(onClickListener);
    }

    @Override
    public void setBackground(Drawable drawable) {
        layoutRoot.setBackgroundDrawable(drawable);
    }

    @Override
    public void setCurrentCityName(String cityName) {
        tvCityName.setText(cityName);
    }

    @Override
    public void setCityList(List<String> cityList) {
        adapter.setData(cityList);
    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.tv_city_name:
                break;
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @SuppressLint("CheckResult")
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            presenter.selectPage(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
