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
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.tyaathome.s1mpleweather.model.bean.city.CityBean;
import com.tyaathome.s1mpleweather.model.bean.city.LocationCityBean;

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
    //private ObservableEmitter emitter;
    private static final boolean TEST = false;
    private OnLocationListener onLocationListener;

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
     * @param listener
     */
    public void startLocation(OnLocationListener listener) {
        startLocation();
        onLocationListener = listener;
    }

    public void stopLocation() {
        if(mAMapLocationClient != null) {
            mAMapLocationClient.stopLocation();
        }
    }

    private void finishLocation() {
        stopLocation();
        if(onLocationListener != null) {
            onLocationListener.onComplete();
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
     * @param aMapBean 定位对象
     */
    private void saveLocationCity(AMapBean aMapBean) {
        // 格式化地址
        String province = aMapBean.getProvince();
        String city = aMapBean.getCity();
        String county = aMapBean.getCounty();
        String street = aMapBean.getStreet();
        if (!TextUtils.isEmpty(county) && street.contains(county)) {
            street = street.substring(street.indexOf(county)
                    + county.length());
        } else if (!TextUtils.isEmpty(city) && street.contains(city)) {
            street = street.substring(street.indexOf(city)
                    + county.length());
        }
        CityBean bean = CityTools.getInstance(mContext).getCity(province, city, county);
        LocationCityBean locationCityBean = new LocationCityBean();
        locationCityBean.setId(bean.getId());
        locationCityBean.setParent_id(bean.getParent_id());
        locationCityBean.setProvince(bean.getProvince());
        locationCityBean.setCity(bean.getCity());
        locationCityBean.setCounty(bean.getCounty());
        locationCityBean.setStreet(street);
        locationCityBean.setLatitude(aMapBean.getLatitude());
        locationCityBean.setLongitude(aMapBean.getLongitude());
        Realm.getDefaultInstance().beginTransaction();
        Realm.getDefaultInstance().copyToRealmOrUpdate(locationCityBean);
        Realm.getDefaultInstance().commitTransaction();
    }

    private AMapLocationListener aMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if(aMapLocation == null || aMapLocation.getLatitude() == 0.0d || aMapLocation.getLongitude() == 0.0d) {
                stopLocation();
                return;
            }
            if(TEST) {
                //searchLocation(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                searchLocation(28.698846,115.868232);
                //searchLocation(31.334647,120.741017);
            } else {
                saveLocationCity(new AMapBean(aMapLocation));
                finishLocation();
            }
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
                if(TEST) {
                    saveLocationCity(new AMapBean(regeocodeResult));
                    finishLocation();
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

    public interface OnLocationListener {
        void onComplete();
        void onStop(String errorMessage);
    }

    private static class AMapBean {
        String province = "";
        String city = "";
        String county = "";
        String street = "";
        double latitude = 0.0d;
        double longitude = 0.0d;

        public AMapBean(AMapLocation aMapLocation) {
            if(aMapLocation != null) {
                province = aMapLocation.getProvince();
                city = aMapLocation.getCity();
                county = aMapLocation.getDistrict();
                street = aMapLocation.getAddress();
                latitude = aMapLocation.getLatitude();
                longitude = aMapLocation.getLongitude();
            }
        }

        public AMapBean(RegeocodeResult result) {
            if(result != null) {
                RegeocodeAddress address = result.getRegeocodeAddress();
                RegeocodeQuery query = result.getRegeocodeQuery();
                if(address != null) {
                    province = address.getProvince();
                    city = address.getCity();
                    county = address.getDistrict();
                    street = address.getFormatAddress();
                }
                if(query != null) {
                    LatLonPoint latLng = query.getPoint();
                    if(latLng != null) {
                        latitude = latLng.getLatitude();
                        longitude = latLng.getLongitude();
                    }
                }
            }
        }

        /**
         * 获取省份名
         * @return
         */
        public String getProvince() {
            return province;
        }

        /**
         * 城市名
         * @return
         */
        public String getCity() {
            return city;
        }

        /**
         * 获取县名
         * @return
         */
        public String getCounty() {
            return county;
        }

        /**
         * 获取街道名
         * @return
         */
        public String getStreet() {
            return street;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

}
