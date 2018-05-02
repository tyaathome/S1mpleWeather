package com.tyaathome.s1mpleweather.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;
import com.tyaathome.s1mpleweather.model.bean.main.sstq.SstqBean;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.contract.CityContract;
import com.tyaathome.s1mpleweather.mvp.presenter.CityPresenter;

@LayoutID(R.layout.fragment_city)
public class CityFragment extends BaseFragment implements CityContract.View {

    private BasePresenter presenter;
    private TextView textView;

    @Override
    protected BasePresenter onLoadPresenter() {
        presenter = new CityPresenter(this);
        return presenter;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        textView = findViewById(R.id.tv_city_name);
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void fillSstqData(SstqBean bean) {

    }
}
