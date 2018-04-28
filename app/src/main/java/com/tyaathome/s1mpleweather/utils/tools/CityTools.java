package com.tyaathome.s1mpleweather.utils.tools;

import android.annotation.SuppressLint;
import android.content.Context;

import com.tyaathome.s1mpleweather.model.bean.city.CityRealmBean;
import com.tyaathome.s1mpleweather.model.bean.city.CityList;
import com.tyaathome.s1mpleweather.model.bean.city.LocationCityRealmBean;
import com.tyaathome.s1mpleweather.utils.CommonUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * 城市信息
 * Created by tyaathome on 2018/2/27.
 */

public class CityTools {

    private Context mContext;
    @SuppressLint("StaticFieldLeak")
    private static CityTools instance;


    private CityTools(Context context) {
        mContext = context;
        init();
    }

    public static synchronized CityTools getInstance(Context context) {
        if (instance == null) {
            instance = new CityTools(context);
        }
        return instance;
    }

    private void init() {
        // 初始化城市列表
        initCityListToRealm();
    }

    /**
     * 初始化城市列表
     */
    @SuppressWarnings("ConstantConditions")
    private void initCityListToRealm() {
        CityList cityList = Realm.getDefaultInstance().where(CityList.class).findFirst();
        if (cityList == null || cityList.getCityList() == null || cityList.getCityList().size() == 0) {
            insertCityListToRealm();
            initCityListToRealm();
        }
    }

    /**
     * 向realm插入城市列表数据
     */
    private void insertCityListToRealm() {
        InputStream inputStream = CommonUtils.getInputStreamFromAssets(mContext, "city_list/citylist.csv");
        if (inputStream != null) {
            InputStreamReader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(inputStream, "utf8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (inputStreamReader != null) {
                Scanner scanner = new Scanner(inputStreamReader);
                RealmList<CityRealmBean> realmList = new RealmList<>();
                while (scanner.hasNext()) {
                    String[] line = parseLine(scanner.nextLine());
                    if (line.length == 7) {
                        // 添加城市列表至realm
                        CityRealmBean bean = new CityRealmBean();
                        bean.setId(line[0]);
                        bean.setParent_id(line[1]);
                        bean.setProvince(line[2]);
                        bean.setCity(line[3]);
                        bean.setCounty(line[4]);
                        bean.setPinyin(line[5]);
                        bean.setPinyin_simple(line[6]);
                        realmList.add(bean);
                    }
                }
                CityList cityList = new CityList();
                cityList.setCityList(realmList);
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(cityList);
                realm.commitTransaction();
            }
        }
    }

    /**
     * 解析csv文件一行数据
     *
     * @return csv文件一行数据
     */
    private String[] parseLine(String value) {
        value = value.trim().replace("\n", "").replace("\r", "");
        return value.split(",");
    }

    /**
     * 获取城市
     *
     * @param province 省
     * @param city     市
     * @param county   县
     * @return 返回城市信息
     */
    public CityRealmBean getCity(String province, String city, String county) {
//        if (province.endsWith("省") || province.endsWith("市")) {
//            province = province.substring(0, province.length() - 1);
//        }
        if (city.endsWith("市")) {
            city = city.substring(0, city.length() - 1);
        }
        CityList cityList = Realm.getDefaultInstance().where(CityList.class).findFirst();
        if (cityList != null) {
            return cityList.getCityList().where()
                    .equalTo("province", province)
                    .equalTo("city", city)
                    .contains("county", county)
                    .findFirst();
        }
        return null;
    }

    /**
     * 获取本地定位城市
     * @return 缓存定位城市
     */
    public LocationCityRealmBean getLocationCity() {
        return Realm.getDefaultInstance()
                .where(LocationCityRealmBean.class)
                .findFirst();
    }

}
