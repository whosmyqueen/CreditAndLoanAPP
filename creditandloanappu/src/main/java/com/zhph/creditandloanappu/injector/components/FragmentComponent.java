package com.zhph.creditandloanappu.injector.components;

import android.app.Activity;

import com.zhph.creditandloanappu.injector.PerFragment;
import com.zhph.creditandloanappu.injector.modules.FragmentModule;

import dagger.Component;

/**
 * Created by whosmyqueen on 16/9/5.
 */
@PerFragment
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

}
