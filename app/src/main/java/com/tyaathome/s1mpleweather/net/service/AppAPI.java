package com.tyaathome.s1mpleweather.net.service;


import com.tyaathome.s1mpleweather.net.pack.base.BasePackDown;
import com.tyaathome.s1mpleweather.net.pack.base.BasePackUp;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 请求api
 * Created by tyaathome on 2016/4/15.
 */
public interface AppAPI {
    @Multipart
    @POST("ztq_sh_jc/service.do")
    Observable<BasePackDown> getData(@Part("p") BasePackUp request);

    @Multipart
    @POST("ztq_sh_jc/service.do")
    Observable<BasePackDown> getData(@Part("file\"; filename=\"pp.png\" ") BasePackUp filerequest, @Part("p")
            BasePackUp request);

    /**
     * 请求包加数据流
     * @param filePart 文件字段
     * @param request 请求包
     * @return 响应包回调
     */
    @Multipart
    @POST("ztq_sh_jc/service.do")
    Observable<BasePackDown> getData(@Part MultipartBody.Part filePart, @Part("p") BasePackUp request);
}
