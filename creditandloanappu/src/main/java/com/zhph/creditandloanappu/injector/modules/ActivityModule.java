package com.zhph.creditandloanappu.injector.modules;

import android.app.Activity;

import com.zhph.creditandloanappu.injector.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zwl on 16/9/5.
 */
@Module
public class ActivityModule {
    private static Activity mActivity;

    public ActivityModule(Activity activity){
        this.mActivity = activity;
    }

    @Provides
    @PerActivity
    public static Activity provideActivity(){
        return mActivity;
    }
}
