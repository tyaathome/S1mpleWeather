package com.tyaathome.s1mpleweather.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;

import com.tyaathome.s1mpleweather.model.bean.city.CityBean;
import com.tyaathome.s1mpleweather.model.bean.city.LocationCityBean;
import com.tyaathome.s1mpleweather.model.bean.main.weekweather.WeekWeatherBean;
import com.tyaathome.s1mpleweather.model.bean.main.weekweather.WeekWeatherInfoBean;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;
import com.tyaathome.s1mpleweather.mvp.contract.MainContract;
import com.tyaathome.s1mpleweather.net.pack.main.week.WeekWeatherPackUp;
import com.tyaathome.s1mpleweather.ui.activity.city.SearchCityActivity;
import com.tyaathome.s1mpleweather.utils.CommonUtils;
import com.tyaathome.s1mpleweather.utils.tools.CityTools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.tyaathome.s1mpleweather.model.constant.ActivityRequestCodeConstant.GOTO_SELECT_CITY;

public class MainPresenter implements MainContract.Presenter {

    private Context mContext;
    private ObservableEmitter<String> mEmitter;
    private MainContract.View mView;
    private static final String TAG = "MainPresenter";
    private List<String> selectedCityList = new ArrayList<>();

    public MainPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void attachView(BaseView view) {
        mView = (MainContract.View) view;
    }

    @Override
    public void start() {
        initBackgroundObservable();
        // 第一次进入默认选中第一个城市
        updateCityList(null);
    }

    private void preperBackgroundData(String cityid, boolean isDelay) {
        if(!TextUtils.isEmpty(cityid)) {
            if(isDelay) {
                if (mEmitter != null && !mEmitter.isDisposed()) {
                    mEmitter.onNext(cityid);
                } else {
                    initBackgroundObservable();
                }
            } else {
                mView.setBackground(getCurrentWeatherById(cityid));
            }
        }
    }

    private void searchCityNameById(String cityid) {
        Observable.just(cityid)
                .map(s -> CityTools.getInstance(mContext).getCityById(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CityBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CityBean cityBean) {
                        if(cityBean != null) {
                            mView.setCurrentCityName(cityBean.getCity());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void selectPage(int position, boolean isDelay) {
        if(selectedCityList.size()> position) {
            String cityId = selectedCityList.get(position);
            preperBackgroundData(cityId, isDelay);
            searchCityNameById(cityId);
            mView.setPagerCurrentItem(position);
        }
    }

    @Override
    public void gotoSelectCity() {
        Intent intent = new Intent(mContext, SearchCityActivity.class);
        Activity activity = (Activity) mContext;
        activity.startActivityForResult(intent, GOTO_SELECT_CITY);
    }

    @Override
    public void updateCityList(CityBean cityBean) {
        selectedCityList.clear();
        LocationCityBean location = CityTools.getInstance(mContext).getLocationCity();
        if(location != null && !TextUtils.isEmpty(location.getId())) {
            selectedCityList.add(location.getId());
        }
        List<CityBean> cityList = CityTools.getInstance(mContext).getSelectedCityList();
        List<String> cityIdList = new ArrayList<>();
        for(CityBean bean : cityList) {
            cityIdList.add(bean.getId());
        }
        selectedCityList.addAll(cityIdList);
        mView.setCityList(selectedCityList);
        if(cityBean == null) {
            selectPage(0, false);
        } else {
            for(int i = 0; i < selectedCityList.size(); i++) {
                String id = selectedCityList.get(i);
                if(id.equals(cityBean.getId())) {
                    mView.setPagerCurrentItem(i);
                }
            }
        }
    }

    /**
     * 初始化更改背景的observable
     */
    private void initBackgroundObservable() {
        createBackgroundObservable().subscribe(observer);
    }

    /**
     * 创建更改背景的observable
     */
    private Observable<BitmapDrawable> createBackgroundObservable() {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> mEmitter = emitter)
                //.skip(1)
                .throttleWithTimeout(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .map(this::getCurrentWeatherById)
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 通过城市id获取天气图片
     * @param cityid
     * @return
     */
    private BitmapDrawable getCurrentWeatherById(String cityid) {
        WeekWeatherPackUp up = new WeekWeatherPackUp(cityid);
        WeekWeatherBean bean = up.getCacheData();
        if(bean != null && bean.getWeek() != null && bean.getWeek().size() > 1) {
            WeekWeatherInfoBean infoBean =  bean.getWeek().get(1);
            if(infoBean != null && !TextUtils.isEmpty(infoBean.getWd_day_ico())) {
                String ico = infoBean.getWd_day_ico();
                return CommonUtils.getWeatherBackground(mContext, ico);
            }
        }
        return null;
    }

    private Observer<BitmapDrawable> observer = new Observer<BitmapDrawable>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(BitmapDrawable drawable) {
            mView.setBackground(drawable);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };
}
