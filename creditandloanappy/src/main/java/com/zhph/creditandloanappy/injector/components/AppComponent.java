package com.zhph.creditandloanappy.injector.components;


import android.text.SpannableStringBuilder;

import com.zhph.creditandloanappy.MyApp;
import com.zhph.creditandloanappy.components.okhttp.OkHttpHelper;
import com.zhph.creditandloanappy.data.api.login.LoginService;
import com.zhph.creditandloanappy.data.api.main.MainService;
import com.zhph.creditandloanappy.injector.modules.ApiModule;
import com.zhph.creditandloanappy.injector.modules.AppModule;

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
