package com.zhph.creditandloanappy.ui.splash;

import android.content.Context;

import com.zhph.creditandloanappy.ui.base.IPresenter;


/**
 * Created by zwl on 16/9/5.
 */
public interface SplashContract {

    interface View {

        void readyGoMain();

        void readyGoLogin();
    }

    interface Presenter extends IPresenter<View> {

        void checkIsFirstIn(Context context);
    }
}
