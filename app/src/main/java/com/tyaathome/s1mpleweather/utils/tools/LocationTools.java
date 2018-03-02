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
import com.tyaathome.s1mpleweather.model.RealmObject.CityRealmBean;
import com.tyaathome.s1mpleweather.model.RealmObject.LocationCityRealmBean;

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

    private LocationTools(Context context) {
        mContext = context;
    }

    public static synchronized LocationTools getInstance(Context context) {
        if(instance == null) {
            instance = new LocationTools(context.getApplicationContext());
        }
        return instance;
    }

    public void init(AMapLocationListener aMapLocationListener) {
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
     * @param aMapLocationListener 高德地图定位回调
     */
    public void startLocation() {
        if(mAMapLocationClient != null) {
            mAMapLocationClient.startLocation();
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

//    /**
//     * 定位回调
//     */
//    private AMapLocationListener aMapLocationListener = new AMapLocationListener() {
//        @Override
//        public void onLocationChanged(AMapLocation aMapLocation) {
//            if(aMapLocation != null) {
//                saveLocationCity(aMapLocation);
//            } else {
//                LocationCityRealmBean bean = Realm.getDefaultInstance().where(LocationCityRealmBean.class).findFirst();
//                if(bean != null) {
//                    searchLocation(bean.getLatitude(), bean.getLongitude());
//                } else {
//                    // TODO: 2018/3/1 第一次启动程序时定位失败处理(启动3次定位，如果3次定位都失败则跳转城市选择页面)
////                    Observable.create(new ObservableOnSubscribe<Object>() {
////                        @Override
////                        public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
////                            startLocation();
////                        }
////                    }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
////                        @Override
////                        public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
////                            return throwableObservable.zipWith(Observable.range(1, 3), new BiFunction<Throwable, Integer, Integer>() {
////
////                                @Override
////                                public Integer apply(Throwable throwable, Integer integer) throws Exception {
////                                    return integer;
////                                }
////                            }).flatMap(new Function<Integer, ObservableSource<?>>() {
////                                @Override
////                                public ObservableSource<?> apply(Integer integer) throws Exception {
////                                    return Observable.timer(integer, TimeUnit.SECONDS);
////                                }
////                            });
////                        }
////                    });
//                }
//            }
//        }
//    };

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
