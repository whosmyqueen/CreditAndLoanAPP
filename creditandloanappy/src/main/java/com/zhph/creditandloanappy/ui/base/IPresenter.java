package com.zhph.creditandloanappy.ui.base;

import android.content.Intent;

/**
 * Created by 郑志辉 on 16/9/30.
 */

public interface IPresenter<T> {

    void attachView(T view, BaseActivity context);

    void detachView();

    /**
     * 处理用户选择的信息
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
