package com.zhph.creditandloanappy.injector.modules;

import com.zhph.creditandloanappy.utils.ModuleUtil;
import com.zhph.creditandloanappy.data.api.login.LoginService;
import com.zhph.creditandloanappy.data.api.main.MainService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by zwl on 16/9/6.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    public MainService provideMainService(OkHttpClient okHttpClient) {
        MainService mainService = (MainService) ModuleUtil.getInstant().createService(okHttpClient, MainService.class);
        return mainService;
    }

    @Provides
    @Singleton
    public LoginService provideLoginService(OkHttpClient okHttpClient) {
        LoginService loginService = (LoginService) ModuleUtil.getInstant().createService(okHttpClient, LoginService.class);
        return loginService;
    }
}
