package com.zhph.creditandloanappu.ui.login;

import android.Manifest;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.zhph.commonlibrary.utils.DialogUtil;
import com.zhph.commonlibrary.views.editTextView.ClearEditText;
import com.zhph.commonlibrary.views.editTextView.TelEditText;
import com.zhph.creditandloanappu.ui.base.BaseActivity;
import com.zhph.creditandloanappu.AppManager;
import com.zhph.creditandloanappu.R;
import com.zhph.creditandloanappu.utils.EventHelper;

import butterknife.Bind;

import static com.zhph.creditandloanappu.R.id.iv_delete_account;
import static com.zhph.creditandloanappu.R.id.iv_delete_pwd;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @Bind(R.id.et_login_phone)
    TelEditText mEtLoginPhone;
    @Bind(iv_delete_account)
    ImageView mIvDeleteAccount;
    @Bind(R.id.et_login_password)
    ClearEditText mEtLoginPassword;
    @Bind(iv_delete_pwd)
    ImageView mIvDeletePwd;
    @Bind(R.id.bt_login)
    Button mBtLogin;
    @Bind(R.id.tv_login_new_user)
    TextView mTvLoginNewUser;
    @Bind(R.id.tv_login_forget_user)
    TextView mTvLoginForgetUser;
    private String userPhone;
    private String client;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }


    @Override
    protected void initEventAndData() {
        EventHelper.click(this, mIvDeleteAccount, mIvDeletePwd, mBtLogin, mTvLoginNewUser, mTvLoginForgetUser);
        mPresenter.checkIsClient(new DialogUtil(this));
        mPresenter.checkIsLogin();
        mEtLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    isShowX(false, mIvDeleteAccount);
                } else {
                    isShowX(true, mIvDeleteAccount);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEtLoginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    isShowX(false, mIvDeletePwd);
                } else {
                    isShowX(true, mIvDeletePwd);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPresenter.checkNewVersion();

        RxPermissions.getInstance(this).request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission
                .WRITE_EXTERNAL_STORAGE).subscribe(granted -> {
            if (granted) {
                // All requested permissions are granted
                mPresenter.checkNewVersion();
            } else {
                // At least one permission is denied
            }
        });


    }


    @Override
    public void start2Activity(Class c) {
        Intent intent = new Intent(this, c);
        intent.putExtra("fragmentIndex", 0);
        startActivity(intent);
    }

    @Override
    public void start2Activity(Intent intent) {
        this.startActivity(intent);
    }


    @Override
    public void onError() {
        mBtLogin.setEnabled(true);
        super.dismissDialog();
    }

    @Override
    public void onSuccess() {
        mBtLogin.setEnabled(true);
        super.dismissDialog();
    }

    @Override
    public void showLoadDialog() {
        mBtLogin.setEnabled(false);
        super.showDialog();
    }

    @Override
    public void showLoadDialog(String loadingText) {
        mBtLogin.setEnabled(false);
        super.showDialog(loadingText);
    }


    @Override
    public void isShowX(boolean isShow, ImageView imageView) {
        if (!isShow) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public String getUserName() {
        return mEtLoginPhone.getText().toString().trim().replace(" ", "");
    }

    @Override
    public String getPassword() {
        return mEtLoginPassword.getText().toString().trim();
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_new_user:
                break;
            case R.id.bt_login:
                mPresenter.login();
                break;
            case iv_delete_account:
                mEtLoginPhone.setText("");
                break;
            case iv_delete_pwd:
                mEtLoginPassword.setText("");
                break;
            case R.id.tv_login_forget_user:
                break;
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            AppManager.getAppManager().AppExit(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
