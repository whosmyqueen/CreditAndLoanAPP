package com.zhph.creditandloanappu.injector.components;


import android.text.SpannableStringBuilder;

import com.zhph.creditandloanappu.MyApp;
import com.zhph.creditandloanappu.components.okhttp.OkHttpHelper;
import com.zhph.creditandloanappu.data.api.login.LoginService;
import com.zhph.creditandloanappu.data.api.main.MainService;
import com.zhph.creditandloanappu.injector.modules.ApiModule;
import com.zhph.creditandloanappu.injector.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zwl on 16/9/5.
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {

    OkHttpHelper getOkHttpHelper();

    MainService getMainService();

    LoginService getLoginService();

    SpannableStringBuilder getSpannableStringBuilder();

    void inject(MyApp mApplication);
}
