package com.tyaathome.s1mpleweather.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.contract.MainContract;
import com.tyaathome.s1mpleweather.mvp.presenter.MainPresenter;
import com.tyaathome.s1mpleweather.ui.adapter.city.CityFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

@LayoutID(R.layout.activity_main)
public class MainActivity extends BaseActivity implements MainContract.View {

    private MainPresenter presenter;
    private ViewPager viewPager;
    private CityFragmentAdapter adapter;
    private TextView tvCityName;
    private int count = 5;
    private List<String> dataList = new ArrayList<>();

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
        for(int i = 1; i <= count; i++) {
            dataList.add(String.valueOf(i));
        }
        adapter = new CityFragmentAdapter(getSupportFragmentManager(), dataList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tvCityName.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_city_name:
                    count++;
                    dataList.clear();
                    for(int i = 1; i <= count; i++) {
                        dataList.add(String.valueOf(i));
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
