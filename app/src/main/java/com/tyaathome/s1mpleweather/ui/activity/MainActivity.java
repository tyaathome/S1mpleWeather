package com.tyaathome.s1mpleweather.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.contract.MainContract;
import com.tyaathome.s1mpleweather.mvp.presenter.MainPresenter;
import com.tyaathome.s1mpleweather.ui.adapter.city.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

@LayoutID(R.layout.activity_main)
public class MainActivity extends BaseActivity implements MainContract.View {

    private MainPresenter presenter;
    private ViewPager viewPager;
    private FragmentAdapter adapter;

    @Override
    protected BasePresenter onLoadPresenter() {
        presenter = new MainPresenter(this);
        return presenter;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        viewPager = findViewById(R.id.viewPager);
    }

    @Override
    public void initEventAndData() {
        List<String> dataList = new ArrayList<>();
        for(int i = 1; i <= 10; i++) {
            dataList.add(String.valueOf(i));
        }
        adapter = new FragmentAdapter(getSupportFragmentManager(), dataList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adapter.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void update() {
        adapter.notifyDataSetChanged();
    }
}
