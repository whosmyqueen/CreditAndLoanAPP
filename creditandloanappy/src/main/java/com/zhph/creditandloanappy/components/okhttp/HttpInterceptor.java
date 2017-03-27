package com.zhph.creditandloanappy.components.okhttp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 郑志辉 on 2016/12/5.
 */

public class HttpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        Request request = builder.addHeader("Content-Type", "text/json;charset=utf-8").build();
        return chain.proceed(request);
    }
}
