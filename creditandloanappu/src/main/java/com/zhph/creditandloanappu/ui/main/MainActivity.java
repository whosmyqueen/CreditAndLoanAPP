package com.zhph.creditandloanappu.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.zhph.commonlibrary.utils.CommonUtil;
import com.zhph.commonlibrary.views.NoScrollViewPager;
import com.zhph.creditandloanappu.adapter.BasePagerAdapter;
import com.zhph.creditandloanappu.global.GlobalAttribute;
import com.zhph.creditandloanappu.ui.base.BaseActivity;
import com.zhph.creditandloanappu.utils.EventHelper;
import com.zhph.creditandloanappu.AppManager;
import com.zhph.creditandloanappu.R;
import com.zhph.creditandloanappu.ui.base.BaseFragment;

import java.util.List;

import butterknife.Bind;

/**
 * Created by zwl on 16/9/5.
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {


    @Bind(R.id.vp_container_main)
    NoScrollViewPager mContainer;
    @Bind(R.id.rb_home_main)
    RadioButton mRbHome;
    @Bind(R.id.rb_order_main)
    RadioButton mRbOrder;
    @Bind(R.id.rb_online_repayment_main)
    RadioButton mRbOnlineRepayment;
    @Bind(R.id.rb_mine_main)
    RadioButton mRbMine;

    private List<Fragment> mFragmentList;

    private BasePagerAdapter mBasePagerAdapter;

    private FragmentManager fm;

    private long mExitTime;


    public static void startActivity(Context context, Class c) {
        Intent intent = new Intent(context, c);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void initEventAndData() {
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        fm = getSupportFragmentManager();
        mPresenter.initAdapter(mContainer, fm);
        EventHelper.click(this, mRbHome, mRbOrder, mRbOnlineRepayment, mRbMine);
        mPresenter.checkNewVersion();
        RxPermissions.getInstance(this).request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(granted -> {
            if (granted) {
                // All requested permissions are granted
//                mPresenter.checkNewVersion();
            } else {
                // At least one permission is denied
            }
        });
        mContainer.setOffscreenPageLimit(3);
    }

    @Override
    public void initAdapter(BasePagerAdapter basePagerAdapter) {
        mContainer.setAdapter(basePagerAdapter);
    }


    @Override
    public void onError() {
        super.dismissDialog();
    }

    @Override
    public void onSuccess() {
        super.dismissDialog();
    }

    @Override
    public void showLoadDialog() {
        super.showDialog();
    }

    @Override
    public void showLoadDialog(String loadingText) {
        super.showDialog(loadingText);
    }

    @Override
    public String getUserPhone() {
        return (String) CommonUtil.get4SP("userPhone", "");
    }

    @Override
    public void start2Activity(Class c) {
        Intent intent = new Intent(MainActivity.this, c);
        intent.putExtra("client", "client");
        startActivity(intent);
        finish();
    }


    @Override
    public BaseFragment getFragment(int index) {
        return null;
    }

    /**
     * 获取侧滑背景并进行模糊 灰白处理
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void getBitmap() {

    }


    @Override
    protected void processClick(View v) {
        mPresenter.checkUserState();
        switch (v.getId()) {
            case R.id.rb_home_main:
                mPresenter.setCurrentShowingPage(0);
                break;
            case R.id.rb_order_main:
                mPresenter.setCurrentShowingPage(1);
                break;
            case R.id.rb_online_repayment_main:
                mPresenter.setCurrentShowingPage(2);
                break;
            case R.id.rb_mine_main:
                mPresenter.setCurrentShowingPage(3);
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, R.string.exit_tip, Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {

                AppManager.getAppManager().AppExit(this);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case GlobalAttribute.SIGN_CODE:
                break;

        }
    }

    @Override
    protected void onResume() {
        mPresenter.checkUserState();
        int fragmengIndex = getIntent().getIntExtra("fragmentIndex", -1);

        if (fragmengIndex == 1 && mContainer.getCurrentItem() != 1) {
            mRbOrder.setChecked(true);
            mPresenter.setCurrentShowingPage(fragmengIndex);
        } else if (fragmengIndex == 0 && mContainer.getCurrentItem() != 0) {
            mRbHome.setChecked(true);
            mPresenter.setCurrentShowingPage(fragmengIndex);
        }
        getIntent().removeExtra("fragmentIndex");
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);// must store the new intent unless getIntent() will
        // return the old one
    }
}
