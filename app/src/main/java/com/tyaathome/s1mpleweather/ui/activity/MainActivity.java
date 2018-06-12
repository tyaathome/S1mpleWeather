package com.tyaathome.s1mpleweather.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;
import com.tyaathome.s1mpleweather.model.bean.city.CityBean;
import com.tyaathome.s1mpleweather.model.constant.IntentExtraConstant;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.contract.MainContract;
import com.tyaathome.s1mpleweather.mvp.presenter.MainPresenter;
import com.tyaathome.s1mpleweather.ui.adapter.city.CityFragmentAdapter;

import java.util.List;

import static com.tyaathome.s1mpleweather.model.constant.ActivityRequestCodeConstant.GOTO_SELECT_CITY;

/**
 * 首页
 */
@LayoutID(R.layout.activity_main)
public class MainActivity extends BaseActivity implements MainContract.View {

    private MainPresenter presenter;
    private ViewPager viewPager;
    private CityFragmentAdapter adapter;
    private TextView tvCityName;
    private ViewGroup layoutRoot;
    private ViewGroup layoutTitle;

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
        layoutTitle = findViewById(R.id.layout_title);
    }

    @Override
    public void initEventAndData() {
        adapter = new CityFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        //viewPager.setOffscreenPageLimit(9);
        layoutTitle.setOnClickListener(onClickListener);
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

    @Override
    public void selectResult(Intent intent) {
        boolean isFresh = intent.getBooleanExtra(IntentExtraConstant.EXTRA_IS_REFRESH_MAIN_CITY_LIST, false);
        if(isFresh) {
            CityBean cityBean = (CityBean) intent.getSerializableExtra(IntentExtraConstant.EXTRA_SELECTED_CITY);
            presenter.updateCityList(cityBean);
        }
    }

    @Override
    public void setPagerCurrentItem(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case GOTO_SELECT_CITY:
                    selectResult(data);
                    break;
            }
        }
    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.layout_title:
                presenter.gotoSelectCity();
                break;
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            presenter.selectPage(position, true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
