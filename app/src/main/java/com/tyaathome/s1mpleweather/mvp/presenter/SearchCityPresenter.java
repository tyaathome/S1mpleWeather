package com.tyaathome.s1mpleweather.mvp.presenter;

import android.content.Context;

import com.tyaathome.s1mpleweather.model.bean.city.CityBean;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;
import com.tyaathome.s1mpleweather.mvp.contract.SearchCityContract;
import com.tyaathome.s1mpleweather.utils.tools.CityTools;

import java.util.List;

/**
 * Created by tyaathome on 2018/05/31.
 */
public class SearchCityPresenter implements SearchCityContract.Presenter {

    private SearchCityContract.View mView;
    private Context mContext;

    public SearchCityPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void attachView(BaseView view) {
        mView = (SearchCityContract.View) view;
    }

    @Override
    public void start() {

    }

    @Override
    public void addCity(CityBean cityBean) {
        CityTools.getInstance(mContext).addCityToSelectedList(cityBean.getId());
        List<CityBean> cityList = CityTools.getInstance(mContext).getSelectedCityList();
        if(cityList != null) {
            mView.updateCityList(cityList);
        }
    }

}
