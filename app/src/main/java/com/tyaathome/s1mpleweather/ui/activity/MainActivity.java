package com.tyaathome.s1mpleweather.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;
import com.tyaathome.s1mpleweather.model.bean.city.LocationCityBean;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.contract.MainContract;
import com.tyaathome.s1mpleweather.mvp.presenter.MainPresenter;
import com.tyaathome.s1mpleweather.ui.adapter.city.CityFragmentAdapter;
import com.tyaathome.s1mpleweather.utils.tools.CityTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@LayoutID(R.layout.activity_main)
public class MainActivity extends BaseActivity implements MainContract.View {

    private MainPresenter presenter;
    private ViewPager viewPager;
    private CityFragmentAdapter adapter;
    private TextView tvCityName;
    private int count = 5;
    private List<String> dataList = new ArrayList<>();
    private String[] cityList = {"1278", "1233", "10955", "1069", "1099", "30828", "1163", "1234", "1214"};

    @Override
    protected BasePresenter onLoadPresenter() {
        presenter = new MainPresenter(this);
        return presenter;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        viewPager = findViewById(R.id.viewPager);
        tvCityName = findViewById(R.id.tv_city_name);
    }

    @Override
    public void initEventAndData() {
//        for(int i = 1; i <= count; i++) {
//            dataList.add(String.valueOf(i));
//        }

        LocationCityBean location = CityTools.getInstance(this).getLocationCity();
        dataList.add(location.getId());
        dataList.addAll(Arrays.asList(cityList));
        adapter = new CityFragmentAdapter(getSupportFragmentManager(), dataList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        //viewPager.setOffscreenPageLimit(9);
        tvCityName.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_city_name:
                    count++;
                    dataList.clear();
                    LocationCityBean location = CityTools.getInstance(MainActivity.this).getLocationCity();
                    dataList.add(location.getId());
                    dataList.addAll(Arrays.asList(cityList));
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
