package com.tyaathome.s1mpleweather.net.factory;


import android.text.TextUtils;
import android.util.Log;

import com.tyaathome.s1mpleweather.net.pack.base.BasePackDown;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackUp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Iterator;

import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 请求和响应对象转换器
 * Created by tyaathome on 2016/4/15.
 */
public class MyConverterFactory extends Converter.Factory{

    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");
    static final MediaType STREAM_TYPE = MediaType.parse("application/octet-stream");
    private static final String TAG = "APP_JSON_DATA";
    public MyConverterFactory() {

    }

    public static MyConverterFactory create() {
        return new MyConverterFactory();
    }

    /**
     * 通过json获取bodyname(默认body中只有一个数据)
     * @param json
     * @return
     */
    private String getBodyName(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                // 找到body JSON
                if(iterator.next().equals("b")) {
                    JSONObject bodyJSON = jsonObject.getJSONObject("b");
                    Iterator<String> bodyIterator = bodyJSON.keys();
                    // 遍历body
                    while (bodyIterator.hasNext()) {
                        // 找到第一个key则为BodyName
                        return bodyIterator.next();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过bodyname获取bodyjson
     * @param json
     * @param bodyname
     * @return
     */
    private String getBodyJSON(String json, String bodyname) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject bodyJSON = jsonObject.getJSONObject("b").getJSONObject(bodyname);
            return bodyJSON.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 响应
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(final Type type, Annotation[] annotations, Retrofit retrofit) {
        return (Converter<ResponseBody, BasePackDown>) value -> {
            String json = value.string();
            String bodyName = getBodyName(json);
            String bodyJson = getBodyJSON(json, bodyName);
            BasePackDown response = NetFactory.getResponse(bodyName);
            if (response == null) {
                return null;
            }
            Realm realm = Realm.getDefaultInstance();
            try {
                response.fillData(realm, new JSONObject(bodyJson));
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(realm != null) {
                    if(realm.isInTransaction()) {
                        realm.cancelTransaction();
                    }
                    if(!realm.isClosed()) {
                        realm.close();
                    }
                }
            }
            Log.e(TAG, "down: " + json);
            return response;
        };
    }

    // 请求

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[]
            methodAnnotations, Retrofit retrofit) {
        return new Converter<BasePackUp, RequestBody>() {
            @Override
            public RequestBody convert(BasePackUp value) throws IOException {
                try {
                    JSONObject object = new JSONObject();
                    JSONObject head = new JSONObject();
                    JSONObject body = new JSONObject();
                    String pid = "";
                    //String pid = PackDataManager.getP();
                    if (TextUtils.isEmpty(pid)) {
                        head.put("p", "");
                    } else {
                        head.put("p", pid);
                    }
                    // 添加头
                    object.put("h", head);
                    // 添加body
                    body.put(value.getName(), value.toJSON());
                    object.put("b", body);
                    Log.e(TAG, "up: " + object.toString());
                    return RequestBody.create(MEDIA_TYPE, object.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };
    }
}
