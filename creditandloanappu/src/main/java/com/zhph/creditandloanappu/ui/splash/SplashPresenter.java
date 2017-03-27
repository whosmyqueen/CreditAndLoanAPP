package com.zhph.creditandloanappu.ui.splash;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhph.creditandloanappu.AppManager;
import com.zhph.creditandloanappu.data.DataManager;
import com.zhph.creditandloanappu.injector.PerActivity;
import com.zhph.creditandloanappu.ui.base.BasePresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by zwl on 16/9/5.
 */
@PerActivity
public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public SplashPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    /**
     * 检测是否第一次启动
     */
    @Override
    public void checkIsFirstIn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("isFirstIn", Context.MODE_PRIVATE);
        Subscription subscription = Observable.timer(4, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).flatMap(aLong -> {
            return mDataManager.isLogin();
        }).subscribe((Action1<Boolean>) isLogin -> {
            if (isLogin) {
                mView.readyGoMain();
            } else{
                mView.readyGoLogin();


            }
            AppManager.getAppManager().finishActivity();

        });
        mRxManager.add(subscription);
    }
}
