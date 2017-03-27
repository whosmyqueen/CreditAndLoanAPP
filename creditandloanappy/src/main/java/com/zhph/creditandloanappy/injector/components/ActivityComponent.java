package com.zhph.creditandloanappy.injector.components;


import com.zhph.creditandloanappy.injector.PerActivity;
import com.zhph.creditandloanappy.injector.modules.ActivityModule;
import com.zhph.creditandloanappy.ui.login.LoginActivity;
import com.zhph.creditandloanappy.ui.main.MainActivity;
import com.zhph.creditandloanappy.ui.splash.SplashActivity;

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
