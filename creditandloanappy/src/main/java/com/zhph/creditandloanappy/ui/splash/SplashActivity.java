package com.zhph.creditandloanappy.ui.splash;

import android.view.View;

import com.zhph.creditandloanappy.ui.base.BaseActivity;
import com.zhph.creditandloanappy.ui.login.LoginActivity;
import com.zhph.creditandloanappu.R;
import com.zhph.creditandloanappy.ui.main.MainActivity;

import butterknife.ButterKnife;

/**
 * 过渡页
 * Created by zwl on 16/9/5.
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {
    @Override
    public void initInjector() {
        mActivityComponent.inject(SplashActivity.this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initEventAndData() {
        setTranslateBar();
        // android隐藏底部虚拟键NavigationBar实现全屏
        ButterKnife.findById(this, R.id.splash_layout).setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        mPresenter.checkIsFirstIn(SplashActivity.this);
    }

    @Override
    protected void processClick(View v) {

    }

    @Override
    public void readyGoMain() {
        MainActivity.startActivity(SplashActivity.this, MainActivity.class);
        finish();
    }

    @Override
    public void readyGoLogin() {
        MainActivity.startActivity(SplashActivity.this, LoginActivity.class);
    }


    @Override
    public void onBackPressed() {
    }
}
