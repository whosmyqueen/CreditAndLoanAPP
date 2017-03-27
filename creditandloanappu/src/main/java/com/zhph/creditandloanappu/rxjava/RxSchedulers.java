package com.zhph.creditandloanappu.rxjava;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Rxjava线程调度
 * Created by 郑志辉 on 16/8/11.
 */
public class RxSchedulers {

    public static Observable.Transformer schedulersTransformer = new Observable.Transformer() {

        @Override
        public Object call(Object observable) {
            return ((Observable) observable).map(loginResultBeanHttpResult -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return loginResultBeanHttpResult;
            }).subscribeOn(Schedulers.newThread()).unsubscribeOn(Schedulers.newThread()).observeOn
                    (AndroidSchedulers.mainThread());
        }
    };
}
