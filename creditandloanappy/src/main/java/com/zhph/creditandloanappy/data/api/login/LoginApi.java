package com.zhph.creditandloanappy.data.api.login;

import com.zhph.commonlibrary.bean.HttpResult;
import com.zhph.commonlibrary.utils.JsonUtil;
import com.zhph.commonlibrary.utils.LogUtils;
import com.zhph.creditandloanappy.bean.LoginResultBean;
import com.zhph.creditandloanappy.rxjava.RxSchedulers;

import javax.inject.Inject;

import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 郑志辉 on 2016/10/28.
 */

public class LoginApi implements LoginService {

    private LoginService mMainService;

    @Inject
    public LoginApi(LoginService mainService) {
        mMainService = mainService;
    }


    @Override
    public Observable<HttpResult<LoginResultBean>> login(@Query("paramJson") String paramJson) {
        return mMainService.login(paramJson).compose(RxSchedulers.schedulersTransformer).map(temp -> {
            LogUtils.e(this, "获取登录结果 : login -> " + JsonUtil.parseBeanToJson(temp));
            return temp;
        });
    }
}
