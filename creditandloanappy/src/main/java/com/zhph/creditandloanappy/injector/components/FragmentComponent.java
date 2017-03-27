package com.zhph.creditandloanappy.injector.components;

import android.app.Activity;

import com.zhph.creditandloanappy.injector.PerFragment;
import com.zhph.creditandloanappy.injector.modules.FragmentModule;

import dagger.Component;

/**
 * Created by whosmyqueen on 16/9/5.
 */
@PerFragment
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

}
