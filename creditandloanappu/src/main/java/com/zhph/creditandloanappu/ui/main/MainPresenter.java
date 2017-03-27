package com.zhph.creditandloanappu.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;

import com.zhph.commonlibrary.bean.HttpResult;
import com.zhph.commonlibrary.utils.CommonUtil;
import com.zhph.commonlibrary.utils.LogUtil;
import com.zhph.creditandloanappu.adapter.BasePagerAdapter;
import com.zhph.creditandloanappu.data.api.main.MainApi;
import com.zhph.creditandloanappu.injector.PerActivity;
import com.zhph.creditandloanappu.ui.base.BaseFragment;
import com.zhph.creditandloanappu.ui.base.BasePresenter;
import com.zhph.creditandloanappu.ui.login.LoginActivity;
import com.zhph.commonlibrary.views.NoScrollViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by 郑志辉 on 2016/10/28.
 */

@PerActivity
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    private List<BaseFragment> mFragmentList;
    private BasePagerAdapter mBasePagerAdapter;

    private NoScrollViewPager mNoScrollViewPager;

    private MainApi mMainApi;

    private BroadcastReceiver broadcastReceiver;

    @Inject
    public MainPresenter(MainApi mainApi) {
        mMainApi = mainApi;
    }

    @Override
    public void initAdapter(NoScrollViewPager viewPager, FragmentManager fragmentManager) {
        mNoScrollViewPager = viewPager;
        mFragmentList = new ArrayList<>();
        mBasePagerAdapter = new BasePagerAdapter(fragmentManager, mFragmentList);
        viewPager.setAdapter(mBasePagerAdapter);
        mView.initAdapter(mBasePagerAdapter);
    }

    @Override
    public void setCurrentShowingPage(int index) {
        if (index <= mFragmentList.size()) {
            mNoScrollViewPager.setCurrentItem(index);
        }
    }

    @Override
    public void checkNewVersion() {
    }

    @Override
    public void checkUserState() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            //jsonObject.put("custNo", "13552411362");
            jsonObject.put("userType", "client");
            jsonObject.put("data", mView.getUserPhone().trim().replace(" ", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Subscription subscription = mMainApi.checkUserState(jsonObject.toString()).subscribe(httpResult -> {
            if (httpResult.getCode() == 200) {
                onSuccess(0, httpResult);
            } else {
                onError(0, httpResult);
            }
        }, throwable -> {
            LogUtil.e("用户检验错误信息：", throwable.toString());
        });
        mRxManager.add(subscription);
    }

    @Override
    public void onError(int type, HttpResult tHttpResult) {
        CommonUtil.clear();
        mView.start2Activity(LoginActivity.class);
    }

    @Override
    public void onSuccess(int type, HttpResult tHttpResult) {

    }

    @Override
    public BaseFragment getFragment(int index) {
        return mFragmentList.get(index);
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
