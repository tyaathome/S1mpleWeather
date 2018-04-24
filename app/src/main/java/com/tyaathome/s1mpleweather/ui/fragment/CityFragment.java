package com.tyaathome.s1mpleweather.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.contract.CityContract;
import com.tyaathome.s1mpleweather.mvp.contract.MainContract;
import com.tyaathome.s1mpleweather.mvp.presenter.CityPresenter;

@LayoutID(R.layout.fragment_city)
public class CityFragment extends BaseFragment implements CityContract.View {

    private BasePresenter presenter;
    private TextView textView;

    @Override
    protected BasePresenter onLoadPresenter() {
        presenter = new CityPresenter(getContext());
        return presenter;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        textView = findViewById(R.id.textView2);
    }

    @Override
    public void initEventAndData() {
        Bundle bundle = getArguments();
        if(bundle != null) {
            String index = bundle.getString("index");
            if(!TextUtils.isEmpty(index)) {
                textView.setText(index);
            } else {
                textView.setText("");
            }
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainContract.View vv = (MainContract.View) getContext();
                vv.update();
            }
        });
    }

}
