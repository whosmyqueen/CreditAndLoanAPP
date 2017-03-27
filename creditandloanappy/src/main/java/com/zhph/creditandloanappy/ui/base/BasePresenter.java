package com.zhph.creditandloanappy.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.view.ViewGroup;

import com.zhph.commonlibrary.utils.CommonUtil;
import com.zhph.creditandloanappu.R;
import com.zhph.creditandloanappy.rxjava.RxManager;


/**
 * Created by 郑志辉 on 16/9/30.
 */

public abstract class BasePresenter<T> implements IPresenter<T> {
    public BaseActivity mActivity;
    public T mView;
    public RxManager mRxManager = new RxManager();

    @Override
    public void attachView(T view, BaseActivity context) {
        this.mView = view;
        this.mActivity = context;
        this.onStart();
    }

    @Override
    public void detachView() {
        this.mView = null;
        mRxManager.clear();
    }

    /**
     * 设置页面是否禁用
     *
     * @param isChange false 禁用 true 可用
     */
    protected void setPageForbidden(boolean isChange) {
        if (!isChange) {
            CommonUtil.forViewChildren((ViewGroup) CommonUtil.getRootView((Activity) mActivity), new int[]{R.id.back1});
        }
    }

    public void onStart() {
    }

    protected void finishSelf() {
        ((BaseActivity) mActivity).finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}