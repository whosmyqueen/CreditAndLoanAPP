package com.zhph.creditandloanappy.ui.login;

import android.widget.ImageView;

import com.zhph.commonlibrary.utils.DialogUtil;
import com.zhph.creditandloanappy.ui.base.IPresenter;
import com.zhph.creditandloanappy.ui.base.IView;

/**
 * Created by Admin on 2016/11/2.
 */

public interface LoginContract {
    /**
     * 视图层需要实现的接口
     */
    interface View extends IView {
        void start2Activity(Class c);


        void onError();

        void onSuccess();

        void showLoadDialog();


        void isShowX(boolean isShow, ImageView imageView);

        String getUserName();

        String getPassword();


    }

    /**
     * 视图层和数据层需要的交互
     */
    interface Presenter extends IPresenter<View> {
        void login();

        boolean checkParamIsVail();


        void checkNewVersion();

        void checkIsLogin();

        void checkIsClient(DialogUtil myDialog);
    }
}
