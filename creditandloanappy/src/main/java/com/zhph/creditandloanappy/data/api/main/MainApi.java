package com.zhph.creditandloanappy.data.api.main;

import com.zhph.commonlibrary.bean.HttpResult;
import com.zhph.creditandloanappy.rxjava.RxSchedulers;

import javax.inject.Inject;

import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 郑志辉 on 2016/10/28.
 */

public class MainApi implements MainService{

    private MainService mMainService;

    @Inject
    public MainApi(MainService mainService) {
        mMainService = mainService;
    }

    @Override
    public Observable<HttpResult> checkUserState(@Query("paramJson") String paramJson) {
        return mMainService.checkUserState(paramJson.toString()).compose(RxSchedulers.schedulersTransformer).map(temp -> {
            return temp;
        });
    }
}
