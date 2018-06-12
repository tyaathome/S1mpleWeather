package com.tyaathome.s1mpleweather.ui.activity.city;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;
import com.tyaathome.s1mpleweather.model.bean.city.CityBean;
import com.tyaathome.s1mpleweather.model.constant.IntentExtraConstant;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.contract.SearchCityContract;
import com.tyaathome.s1mpleweather.mvp.presenter.SearchCityPresenter;
import com.tyaathome.s1mpleweather.ui.activity.BaseActivity;
import com.tyaathome.s1mpleweather.ui.adapter.city.CityResultAdapter;
import com.tyaathome.s1mpleweather.ui.adapter.city.PopularCityAdapter;
import com.tyaathome.s1mpleweather.ui.adapter.decoration.SpaceItemDecoration;
import com.tyaathome.s1mpleweather.utils.CommonUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by tyaathome on 2018/05/31.
 * 搜索城市
 */
@LayoutID(R.layout.activity_search_city)
public class SearchCityActivity extends BaseActivity implements SearchCityContract.View {

    private SearchCityPresenter mPresenter;
    private RecyclerView rvPopularCity;
    private PopularCityAdapter popularCityAdapter;
    private EditText etSearchCity;
    private ViewStub viewStub;
    private RecyclerView rvResultCity;
    private CityResultAdapter cityResultAdapter;

    @Override
    protected BasePresenter onLoadPresenter() {
        mPresenter = new SearchCityPresenter(this);
        return mPresenter;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initPopularCityList();
        etSearchCity = findViewById(R.id.et_search_city);
        viewStub = findViewById(R.id.layout_recycleview);
    }

    @SuppressLint("CheckResult")
    @Override
    public void initEventAndData() {
        popularCityAdapter.getPositionClicks().subscribe(cityClick);
        etSearchCity.addTextChangedListener(textWatcher);
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
        if(rvResultCity != null) {
            rvResultCity.setVisibility(View.INVISIBLE);
        }
        popularCityAdapter.updateSelectedCityList(cityList);
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateResultCityList(String key, List<CityBean> cityList) {
        if(cityList != null && cityList.size() > 0)
        if(rvResultCity == null) {
            rvResultCity = (RecyclerView) viewStub.inflate();
            rvResultCity.setLayoutManager(new LinearLayoutManager(this));
            cityResultAdapter = new CityResultAdapter(key, cityList);
            cityResultAdapter.getPositionClicks().subscribe(cityClick);
            rvResultCity.setAdapter(cityResultAdapter);
        } else {
            rvResultCity.setVisibility(View.VISIBLE);
            cityResultAdapter.updateData(key, cityList);
        }

    }

    @Override
    public void gotoCity(CityBean cityBean, boolean isfresh) {
        Intent intent = new Intent();
        intent.putExtra(IntentExtraConstant.EXTRA_IS_REFRESH_MAIN_CITY_LIST, isfresh);
        intent.putExtra(IntentExtraConstant.EXTRA_SELECTED_CITY, cityBean);
        setResult(RESULT_OK, intent);
        finish();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String key = s.toString();
            if(!TextUtils.isEmpty(key)) {
                mPresenter.queryKey(s.toString());
            } else {
                if(rvResultCity != null) {
                    rvResultCity.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private Consumer<CityBean> cityClick = cityBean -> {
        mPresenter.addCity(cityBean);
    };
}
