package com.zhph.creditandloanappy.utils;


import com.zhph.creditandloanappy.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by whosmyqueen on 2016/10/29.
 */

public class ModuleUtil {
    private static ModuleUtil instance;

    private ModuleUtil() {
    }

    public static ModuleUtil getInstant() {
        if (instance == null) {
            synchronized (ModuleUtil.class) {
                instance = new ModuleUtil();
            }
        }
        return instance;
    }

    public Object createService(OkHttpClient okHttpClient, Class tClass) {
        ;
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)//设置网络访问框架
                .addConverterFactory(GsonConverterFactory.create())//添加json转换框架
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//让Retrofit支持RxJava
                .baseUrl(Constants.SERVICE_URL)
                .build();
        Object service = retrofit.create(tClass);
        return service;
    }

}
