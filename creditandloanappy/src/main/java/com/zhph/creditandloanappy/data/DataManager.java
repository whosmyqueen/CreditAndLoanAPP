package com.zhph.creditandloanappy.data;

import android.content.SharedPreferences;
import android.text.TextUtils;


import com.zhph.commonlibrary.utils.CommonUtil;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by 郑志辉 on 16/9/5.
 */
public class DataManager {

    @Inject
    public DataManager() {

    }

    public Observable<Boolean> getIsFirstIn(final SharedPreferences preferences) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean isFirstIn = preferences.getBoolean("isFirstIn", true);
                subscriber.onNext(isFirstIn);
                subscriber.onCompleted();
            }
        });
    }


    public Observable<Boolean> isLogin(){
        String userPhone = (String) CommonUtil.get4SP("userPhone","");
        if (TextUtils.isEmpty(userPhone)){
            return Observable.just(false);
        }else {
            return Observable.just(true);
        }
    }
}
