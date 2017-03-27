package com.zhph.creditandloanappy.ui.main;

import android.support.v4.app.FragmentManager;

import com.zhph.commonlibrary.bean.HttpResult;
import com.zhph.commonlibrary.views.NoScrollViewPager;
import com.zhph.creditandloanappy.adapter.BasePagerAdapter;
import com.zhph.creditandloanappy.ui.base.BaseFragment;
import com.zhph.creditandloanappy.ui.base.IPresenter;
import com.zhph.creditandloanappy.ui.base.IView;

/**
 * Created by 郑志辉 on 2016/10/28.
 */

public interface MainContract {

    /**
     * 视图层需要实现的接口
     */
    interface View extends IView {
        void initAdapter(BasePagerAdapter basePagerAdapter);


        void onError();

        void onSuccess();


        String getUserPhone();

        void start2Activity(Class c);

        BaseFragment getFragment(int index);
    }

    /**
     * 视图层和数据层需要的交互
     */
    interface Presenter extends IPresenter<View> {

        void initAdapter(NoScrollViewPager viewPager, FragmentManager fragmentManager);

        void setCurrentShowingPage(int index);

        void checkNewVersion();

        void checkUserState();

        void onError(int type, HttpResult tHttpResult);

        void onSuccess(int type, HttpResult tHttpResult);

        BaseFragment getFragment(int index);
    }
}
