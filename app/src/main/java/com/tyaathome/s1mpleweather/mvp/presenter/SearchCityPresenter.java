package com.tyaathome.s1mpleweather.mvp.presenter;

import android.app.Activity;
import android.content.Context;

import com.tyaathome.s1mpleweather.model.bean.city.CityBean;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;
import com.tyaathome.s1mpleweather.mvp.contract.SearchCityContract;
import com.tyaathome.s1mpleweather.net.service.PackDataManager;
import com.tyaathome.s1mpleweather.utils.CommonUtils;
import com.tyaathome.s1mpleweather.utils.manager.AutoDownloadManager;
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
        CommonUtils.hideKeyboard((Activity) mContext);
    }

    @Override
    public void addCity(CityBean cityBean) {
        if(cityBean != null) {
            CityTools.getInstance(mContext).addCityToSelectedList(cityBean.getId());
            List<CityBean> cityList = CityTools.getInstance(mContext).getSelectedCityList();
            if (cityList != null) {
                mView.updateCityList(cityList);
            }
            PackDataManager.requestList(AutoDownloadManager.getMainData(cityBean.getId()), () -> {
                CityTools.getInstance(mContext).setCityRequestTime(cityBean.getId());
                mView.gotoCity(cityBean, true);
            });
        }
    }

    @Override
    public void queryKey(String key) {
        List<CityBean> cityList = CityTools.getInstance(mContext).getCityListByKey(key);
        mView.updateResultCityList(key, cityList);
    }

}
