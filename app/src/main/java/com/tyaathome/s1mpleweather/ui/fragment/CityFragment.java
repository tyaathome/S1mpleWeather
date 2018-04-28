package com.tyaathome.s1mpleweather.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;
import com.tyaathome.s1mpleweather.model.bean.main.sstq.SstqBean;
import com.tyaathome.s1mpleweather.model.bean.main.weekweather.WeekWeatherBean;
import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.contract.CityContract;
import com.tyaathome.s1mpleweather.mvp.presenter.CityPresenter;

import io.realm.Realm;

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
        textView = findViewById(R.id.tv_city_name);
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void fillTextView() {
        Bundle bundle = getArguments();
        if(bundle != null) {
            String key = bundle.getString("key");
            long start = System.currentTimeMillis();
            WeekWeatherBean bean = new WeekWeatherBean();
            bean.setKey(key);
            SstqBean sstqBean = Realm.getDefaultInstance().where(SstqBean.class).equalTo("key", key).findFirst();
            Log.e("time__", String.valueOf(System.currentTimeMillis()-start));
            if(!TextUtils.isEmpty(key)) {
                textView.setText(key);
            } else {
                textView.setText("");
            }
        }
    }
}
