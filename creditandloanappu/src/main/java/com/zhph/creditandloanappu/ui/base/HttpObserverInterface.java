package com.zhph.creditandloanappu.ui.base;

import com.zhph.commonlibrary.bean.HttpResult;

/**
 * Created by 郑志辉 on 2017/2/27.
 */

public interface HttpObserverInterface<T> {
    void onSuccess(HttpResult<T> httpResult);
}
