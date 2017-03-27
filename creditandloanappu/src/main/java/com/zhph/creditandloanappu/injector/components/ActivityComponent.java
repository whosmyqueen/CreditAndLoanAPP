package com.zhph.creditandloanappu.injector.components;


import com.zhph.creditandloanappu.injector.PerActivity;
import com.zhph.creditandloanappu.injector.modules.ActivityModule;
import com.zhph.creditandloanappu.ui.login.LoginActivity;
import com.zhph.creditandloanappu.ui.main.MainActivity;
import com.zhph.creditandloanappu.ui.splash.SplashActivity;

import dagger.Component;

/**
 * Created by zwl on 16/9/5.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

}
