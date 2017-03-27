package com.zhph.creditandloanappy.data.api.login;

import com.zhph.commonlibrary.bean.HttpResult;
import com.zhph.creditandloanappy.bean.LoginResultBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 郑志辉 on 2016/10/28.
 */

public interface LoginService {
    @POST("LoginByUser.spring")
    Observable<HttpResult<LoginResultBean>> login(@Query("paramJson") String paramJson);

}
