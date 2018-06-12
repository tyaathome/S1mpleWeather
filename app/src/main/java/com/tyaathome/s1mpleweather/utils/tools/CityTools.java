package com.tyaathome.s1mpleweather.utils.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.tyaathome.s1mpleweather.model.bean.city.CityBean;
import com.tyaathome.s1mpleweather.model.bean.city.CityList;
import com.tyaathome.s1mpleweather.model.bean.city.LocationCityBean;
import com.tyaathome.s1mpleweather.model.bean.city.SelectedCityList;
import com.tyaathome.s1mpleweather.utils.CommonUtils;
import com.tyaathome.s1mpleweather.utils.RealmUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

import io.realm.Case;
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
        initSelectedCityList();
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
                RealmList<CityBean> realmList = new RealmList<>();
                while (scanner.hasNext()) {
                    String[] line = parseLine(scanner.nextLine());
                    if (line.length == 7) {
                        // 添加城市列表至realm
                        CityBean bean = new CityBean();
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
    public CityBean getCity(String province, String city, String county) {
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

    public CityBean getCityById(String cityId) {
        if(!TextUtils.isEmpty(cityId)) {
            Realm realm = Realm.getDefaultInstance();
            CityList cityList = realm.where(CityList.class).findFirst();
            if (cityList != null) {
                CityBean cityBean = cityList.getCityList().where().equalTo("id", cityId).findFirst();
                if (cityBean != null) {
                    return realm.copyFromRealm(cityBean);
                }
            }
        }
        return null;
    }

    /**
     * 获取本地定位城市
     * @return 缓存定位城市
     */
    public LocationCityBean getLocationCity() {
        LocationCityBean bean = Realm.getDefaultInstance()
                .where(LocationCityBean.class)
                .findFirst();
        if(bean != null) {
            return RealmUtils.unmanage(bean);
        }
        return null;
    }

    public void initSelectedCityList() {
        SelectedCityList bean = Realm.getDefaultInstance().where(SelectedCityList.class).findFirst();
        if(bean == null) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(new SelectedCityList());
            realm.commitTransaction();
        }
    }

    public CityList getCityList() {
        CityList cityList = Realm.getDefaultInstance().where(CityList.class).findFirst();
        return RealmUtils.unmanage(cityList);
    }

    private CityList getCityListWithoutUnmanage() {
        return Realm.getDefaultInstance().where(CityList.class).findFirst();
    }

    /**
     * 添加城市
     * @param cityid
     */
    public void addCityToSelectedList(String cityid) {
        CityBean cityBean = getCityById(cityid);
        if(cityBean != null) {
            Realm realm = Realm.getDefaultInstance();
            SelectedCityList bean = realm.where(SelectedCityList.class).findFirst();
            if(bean != null) {
                List<CityBean> cityList = bean.getCityList().where().equalTo("id", cityid).findAll();
                if(cityList != null && cityList.size() == 0) {
                    realm.beginTransaction();
                    bean.getCityList().add(cityBean);
                    realm.commitTransaction();
                }
            }
        }
    }

    /**
     * 获取已选中的城市列表
     * @return
     */
    public List<CityBean> getSelectedCityList() {
        SelectedCityList bean = Realm.getDefaultInstance().where(SelectedCityList.class).findFirst();
        if(bean != null) {
            SelectedCityList list = RealmUtils.unmanage(bean);
            if(list != null) {
                return list.getCityList();
            }
        }
        return null;
    }

    /**
     * 通过关键字搜索城市列表
     * @param key
     * @return
     */
    public List<CityBean> getCityListByKey(String key) {
        CityList cityList = getCityListWithoutUnmanage();
        if(cityList != null && cityList.getCityList() != null) {
            List<CityBean> cityBeanList = cityList.getCityList().where()
                    .contains("province", key)
                    .or()
                    .contains("city", key)
                    .or()
                    .contains("county", key)
                    .or()
                    .contains("pinyin", key, Case.INSENSITIVE)
                    .or()
                    .contains("pinyin_simple", key, Case.INSENSITIVE)
                    .findAll();
            return RealmUtils.unmanage(cityBeanList);
        }
        return null;
    }

}
