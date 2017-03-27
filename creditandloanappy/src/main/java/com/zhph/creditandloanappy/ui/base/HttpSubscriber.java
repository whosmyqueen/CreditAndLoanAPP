package com.zhph.creditandloanappy.ui.base;

import com.zhph.commonlibrary.bean.HttpResult;
import com.zhph.commonlibrary.utils.LogUtil;
import com.zhph.commonlibrary.utils.LogUtils;
import com.zhph.creditandloanappu.R;

import rx.Subscriber;


/**
 * Created by 郑志辉 on 2017/2/27.
 */

public class HttpSubscriber<T> extends Subscriber<HttpResult<T>> {

    private IView mIView;
    private BaseActivity mBaseActivity;
    private HttpObserverInterface mInterface;

    public HttpSubscriber(IView IView, BaseActivity baseActivity, HttpObserverInterface<T> observerInterface) {
        mIView = IView;
        mBaseActivity = baseActivity;
        mInterface = observerInterface;
    }

    public HttpSubscriber(IView IView, BaseActivity baseActivity) {
        mIView = IView;
        mBaseActivity = baseActivity;
    }


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e(mBaseActivity.getClass().getName() + " -> " + e.getMessage());
        if (e != null) {
            if (e.toString().contains("SocketTimeoutException")) {
                mBaseActivity.toastNoNot(mBaseActivity.getString(R.string.service_timeout));
            } else {
                mBaseActivity.toastNoNot(mBaseActivity.getString(R.string.service_error));
            }
            LogUtils.e(this, e.toString());
            e.printStackTrace();
        } else {
            mBaseActivity.toastNoNot(mBaseActivity.getString(R.string.service_timeout));
        }
        mIView.dismissDialog();


    }

    @Override
    public void onNext(HttpResult<T> result) {
        if (result.getCode() == 200 || result.getCode() == 20001) {
            if (mInterface != null) {
                mInterface.onSuccess(result);
            } else {
                mIView.showMessage(result.getMessage());
            }
        } else if (result.getCode() != 400) {
            mIView.showMessage(result.getMessage());
        }

        mIView.dismissDialog();
    }


}
