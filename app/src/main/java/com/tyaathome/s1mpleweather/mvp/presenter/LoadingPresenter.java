package com.tyaathome.s1mpleweather.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.tyaathome.s1mpleweather.model.RealmObject.CityRealmBean;
import com.tyaathome.s1mpleweather.model.RealmObject.LocationCityRealmBean;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;
import com.tyaathome.s1mpleweather.mvp.contract.LoadingContract;
import com.tyaathome.s1mpleweather.utils.tools.CityTools;
import com.tyaathome.s1mpleweather.utils.tools.LocationTools;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.realm.Realm;

/**
 * loading presenter
 * Created by tyaathome on 2018/2/24.
 */

public class LoadingPresenter implements LoadingContract.Presenter {

    private LoadingContract.View mView;
    private Context mContext;
    private ObservableEmitter<Integer> mEmitter;
    private static final int RETRY_COUNT = 3;
    private int currentRetryTime = 0;

    public LoadingPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void beginLocation() {
        LocationTools.getInstance(mContext).init(new AMapLocationListener() {
            @Override
            public void onLocationChanged(final AMapLocation aMapLocation) {
//                if(aMapLocation == null) {
//                    mEmitter.onError(new RuntimeException("amaplication is null"));
//                }
                if(!mEmitter.isDisposed()) {
                    mEmitter.onError(new RuntimeException("amaplication is null"));
                }
            }
        });

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                mEmitter = emitter;
                LocationTools.getInstance(mContext).startLocation();
            }
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                return throwableObservable.zipWith(Observable.range(0, RETRY_COUNT), new BiFunction<Throwable, Integer, Integer>() {

                    @Override
                    public Integer apply(Throwable throwable, Integer integer) throws Exception {
                        if(integer >= RETRY_COUNT) {
                            Toast.makeText(mContext, "finish", Toast.LENGTH_LONG).show();
                        }
                        return integer;
                    }
                });
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("Tag", String.valueOf(integer));
            }
        });
        //LocationTools.getInstance(mContext).startLocation();

    }

    @Override
    public void attachView(BaseView view) {
        mView = (LoadingContract.View) view;
    }

    @Override
    public void start() {

    }

    /**
     * 保存定位城市
     * @param aMapLocation 定位对象
     */
    private void saveLocationCity(AMapLocation aMapLocation) {
        // 格式化地址
        String province = aMapLocation.getProvince();
        String city = aMapLocation.getCity();
        String county = aMapLocation.getDistrict();
        String street = aMapLocation.getAddress();
        if (!TextUtils.isEmpty(county) && street.contains(county)) {
            street = street.substring(street.indexOf(county)
                    + county.length());
        } else if (!TextUtils.isEmpty(city) && street.contains(city)) {
            street = street.substring(street.indexOf(city)
                    + county.length());
        }
        CityRealmBean bean = CityTools.getInstance(mContext).getCity(province, city, county);
        LocationCityRealmBean locationCityRealmBean = new LocationCityRealmBean();
        locationCityRealmBean.setId(bean.getId());
        locationCityRealmBean.setParent_id(bean.getParent_id());
        locationCityRealmBean.setProvince(bean.getProvince());
        locationCityRealmBean.setCity(bean.getCity());
        locationCityRealmBean.setCounty(bean.getCounty());
        locationCityRealmBean.setStreet(street);
        locationCityRealmBean.setLatitude(aMapLocation.getLatitude());
        locationCityRealmBean.setLongitude(aMapLocation.getLongitude());
        Realm.getDefaultInstance().beginTransaction();
        Realm.getDefaultInstance().copyToRealmOrUpdate(locationCityRealmBean);
        Realm.getDefaultInstance().commitTransaction();
    }
}
