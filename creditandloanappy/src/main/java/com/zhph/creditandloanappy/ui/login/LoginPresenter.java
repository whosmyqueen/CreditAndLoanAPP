package com.zhph.creditandloanappy.ui.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.zhph.commonlibrary.utils.CommonUtil;
import com.zhph.commonlibrary.utils.DialogUtil;
import com.zhph.commonlibrary.utils.StringUtil;
import com.zhph.commonlibrary.utils.interfaces.DialogInterface;
import com.zhph.creditandloanappy.data.api.login.LoginApi;
import com.zhph.creditandloanappy.ui.base.BasePresenter;
import com.zhph.creditandloanappy.bean.LoginResultBean;
import com.zhph.creditandloanappy.global.GlobalAttribute;
import com.zhph.creditandloanappy.ui.base.HttpSubscriber;
import com.zhph.creditandloanappy.ui.main.MainActivity;
import com.zhph.commonlibrary.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by Admin on 2016/11/2.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private LoginApi mLoginApi;
    private BroadcastReceiver broadcastReceiver;

    @Inject
    public LoginPresenter(LoginApi loginApi) {
        mLoginApi = loginApi;
    }

    @Override
    public void login() {

        if (!checkParamIsVail()) {
            return;
        }
        mView.showLoadDialog();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", StringUtil.Decode64(mView.getPassword()));
            jsonObject.put("username", mView.getUserName());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Subscription subscription = mLoginApi.login(jsonObject.toString()).subscribe(new
                HttpSubscriber<LoginResultBean>(mView, mActivity, httpResult -> {
            //用户姓名
            CommonUtil.set2SP("realName", httpResult.getData().realname);

            CommonUtil.set2SP(GlobalAttribute.LOGIN_NAME, mView.getUserName().replaceAll(" ", ""));

            if (!TextUtils.isEmpty(httpResult.getData().fourstate)) {
                CommonUtil.set2SP(GlobalAttribute.AUTONYM, httpResult.getData().fourstate);
            }
            CommonUtil.set2SP("cardNum", httpResult.getData().cardno);
            CommonUtil.set2SP(GlobalAttribute.CUST_NO, httpResult.getData().custno);
            if (!TextUtils.isEmpty(httpResult.getData().card_num)) {
                CommonUtil.set2SP("bankCardNum", httpResult.getData().card_num);
            }
            mView.onSuccess();
            mView.start2Activity(MainActivity.class);
            finishSelf();
        }));

        mRxManager.add(subscription);
    }

    @Override
    public boolean checkParamIsVail() {


        if (TextUtils.isEmpty(mView.getUserName())) {
            ToastUtil.showToast("手机号不能为空");
            return false;
        }


        if (!CommonUtil.checkPhone(mView.getUserName())) {
            ToastUtil.showToast("手机号格式错误");
            return false;
        }

        if (TextUtils.isEmpty(mView.getPassword())) {
            ToastUtil.showToast("密码不能为空");
            return false;
        }


        if (!CommonUtil.checkPassWord(mView.getPassword())) {
            ToastUtil.showToast("密码格式错误");
            return false;
        }

        return true;
    }

    @Override
    public void checkNewVersion() {

    }

    @Override
    public void checkIsLogin() {
        String temp = CommonUtil.get4SP("userPhone", "").toString();
        if (!TextUtils.isEmpty(temp)) {
            mView.start2Activity(MainActivity.class);
            finishSelf();
        }
    }

    @Override
    public void checkIsClient(DialogUtil myDialog) {
        if (myDialog == null) {
            return;
        }
        //获取用户状态 如果不为空 则用户被禁用 弹框提示
        String client = mActivity.getIntent().getStringExtra("client");
        if (!TextUtils.isEmpty(client)) {
            myDialog.toastDlg("我知道了", new DialogInterface() {
                @Override
                public void sure(Object object) {
                    myDialog.toastDlgdismiss();
                }

                @Override
                public void cancel(Object object) {

                }
            }, "请注意!!!", "您的用户状态已禁用", " ");
        }
    }


    private void broadcastReceiverListener() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int state = intent.getIntExtra("state", 0);
                switch (state) {
                    case 0:
                        mView.onError();
                        break;
                    case 1:
                        mView.onSuccess();
                        break;
                }
                context.unregisterReceiver(this);
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.zhfq");
        mActivity.registerReceiver(broadcastReceiver, filter);
    }

}
