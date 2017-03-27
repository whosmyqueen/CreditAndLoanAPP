package com.zhph.creditandloanappu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhph.commonlibrary.utils.CommonUtil;
import com.zhph.creditandloanappu.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郑志辉 on 16/9/30.
 */

public class BasePagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private List<String> mTitles;

    public BasePagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    public BasePagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList, List<String> titles) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.mTitles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return !CommonUtil.isNullOrEmpty(mTitles) ? mTitles.get(position) : "";
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
