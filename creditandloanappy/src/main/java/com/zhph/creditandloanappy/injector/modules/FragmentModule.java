package com.zhph.creditandloanappy.injector.modules;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.zhph.creditandloanappy.injector.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zwl on 16/9/5.
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment){
        this.mFragment = fragment;
    }

    @Provides
    @PerFragment
    public Activity provideActivity(){
        return mFragment.getActivity();
    }
}
