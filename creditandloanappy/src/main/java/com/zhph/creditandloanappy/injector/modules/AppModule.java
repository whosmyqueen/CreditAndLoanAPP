package com.zhph.creditandloanappy.injector.modules;

import android.app.Application;
import android.text.SpannableStringBuilder;

import com.zhph.commonlibrary.utils.LogUtil;
import com.zhph.creditandloanappy.components.okhttp.HttpInterceptor;
import com.zhph.creditandloanappy.components.okhttp.OkHttpHelper;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by zwl on 16/9/5.
 */
@Module
public class AppModule {
    private Application mApplication;
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            LogUtil.e("OkHttpClient", "OkHttpMessage:" + message);
        }
    });
    public AppModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    @Named("api")
    OkHttpClient provideApiOkHttpClient() {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(new HttpInterceptor()).connectTimeout(10, TimeUnit.SECONDS).addInterceptor(logging)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(200, TimeUnit.SECONDS);
        return builder.build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(@Named("api") OkHttpClient mOkHttpClient) {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = mOkHttpClient.newBuilder().addInterceptor(new HttpInterceptor()).addInterceptor(logging)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(200, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

//        builder.interceptors().clear();
        return builder.build();
    }

    @Provides
    @Singleton
    OkHttpHelper provideOkHttpHelper(OkHttpClient mOkHttpClient) {
        return new OkHttpHelper(mOkHttpClient);
    }

    @Provides
    @Singleton
    SpannableStringBuilder provideSpannableStringBuilder() {
        return new SpannableStringBuilder();
    }

}
