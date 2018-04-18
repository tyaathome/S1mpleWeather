package com.tyaathome.s1mpleweather.utils.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.tyaathome.s1mpleweather.model.RealmObject.city.CityRealmBean;
import com.tyaathome.s1mpleweather.model.RealmObject.city.LocationCityRealmBean;

import io.reactivex.ObservableEmitter;
import io.realm.Realm;

/**
 * 定位工具类
 * Created by tyaathome on 2018/2/26.
 */

public class LocationTools {

    @SuppressLint("StaticFieldLeak")
    private static LocationTools instance;
    private Context mContext;
    // 定位客户端
    private AMapLocationClient mAMapLocationClient = null;
    private GeocodeSearch geocodeSearch;
    // 定位间隔
    private static final long LOCATION_TIME = 5 * 60 * 1000;
    // 定位回调
    private OnCompleteListener mOnCompleteListener;
    private ObservableEmitter emitter;

    private LocationTools(Context context) {
        mContext = context;
        init();
    }

    public static synchronized LocationTools getInstance(Context context) {
        if(instance == null) {
            instance = new LocationTools(context.getApplicationContext());
        }
        return instance;
    }

    public void init() {
        if(mAMapLocationClient == null) {
            mAMapLocationClient = new AMapLocationClient((mContext));
        }
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        option.setInterval(LOCATION_TIME);
        mAMapLocationClient.setLocationListener(aMapLocationListener);
        mAMapLocationClient.setLocationOption(option);
        // 地理信息搜索
        initGeocodeSearch();
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        if(mAMapLocationClient != null) {
            mAMapLocationClient.startLocation();
        }
    }

    /**
     * 开始定位
     * @param emitter
     */
    public void startLocation(ObservableEmitter emitter) {
        startLocation();
        this.emitter = emitter;
    }

    public void stopLocation(String message) {
        if(mAMapLocationClient != null) {
            mAMapLocationClient.stopLocation();
        }
        if(emitter != null && !emitter.isDisposed()) {
            emitter.onError(new RuntimeException(message));
        }
    }

    private void finishLocation() {
        if(emitter != null && !emitter.isDisposed()) {
            emitter.onComplete();
        }
    }

    /**
     * 初始化地理信息搜索
     */
    private void initGeocodeSearch() {
        if(geocodeSearch == null) {
            geocodeSearch = new GeocodeSearch(mContext);
            geocodeSearch.setOnGeocodeSearchListener(onGeocodeSearchListener);
        }
    }

    public void setOnLocationListener(OnCompleteListener listener) {
        mOnCompleteListener = listener;
    }

    /**
     * 逆地理编码搜索位置信息
     */
    private void searchLocation(double latitude, double longitude) {
        LatLonPoint point = new LatLonPoint(latitude, longitude);
        //LatLonPoint point = new LatLonPoint(22.135535, 113.572138);
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(point, 200, GeocodeSearch.AMAP);
        initGeocodeSearch();
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);
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

    private AMapLocationListener aMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if(aMapLocation == null) {
                stopLocation("location error");
                return;
            }
            saveLocationCity(aMapLocation);
            finishLocation();
        }
    };

    /**
     * 逆地理编码回调
     */
    private GeocodeSearch.OnGeocodeSearchListener onGeocodeSearchListener = new GeocodeSearch.OnGeocodeSearchListener() {

        @Override
        public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
            if(i == AMapException.CODE_AMAP_SUCCESS && regeocodeResult != null
                    && regeocodeResult.getRegeocodeAddress() != null
                    && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                // 保存城市
                //saveLocationCity(regeocodeResult);
                if(mOnCompleteListener != null) {
                    mOnCompleteListener.OnComplete();
                }
            }
        }

        @Override
        public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

        }
    };

    /**
     * 定位完成回调
     */
    public interface OnCompleteListener {
        void OnComplete();
    }

}
