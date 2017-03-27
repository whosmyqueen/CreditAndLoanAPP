package com.zhph.creditandloanappy.data.api.main;

import com.zhph.commonlibrary.bean.HttpResult;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 郑志辉 on 2016/10/28.
 */

public interface MainService {

    /**
     * 检查用户状态
     * @return
     */
    @POST("CheckUserIsValid.spring")
    Observable<HttpResult> checkUserState(@Query("paramJson") String paramJson);

}
