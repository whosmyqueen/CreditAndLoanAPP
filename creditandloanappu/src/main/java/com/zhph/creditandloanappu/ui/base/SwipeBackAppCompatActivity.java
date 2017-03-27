package com.zhph.creditandloanappu.ui.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhph.commonlibrary.views.SwipeBackLayout;


/**
 * Created by 郑志辉 on 16/8/29.
 * 滑动返回,具体资料参考https://github.com/freecats/SwipeBackDemo
 */
public class SwipeBackAppCompatActivity extends AppCompatActivity implements SwipeBackLayout.SwipeBackListener {

    private SwipeBackLayout mSwipeBackLayout;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        this.setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(getContainer());
        mSwipeBackLayout.addView(view);
    }

    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        mSwipeBackLayout = new SwipeBackLayout(this);
        mSwipeBackLayout.setOnSwipeBackListener(this);
        container.addView(mSwipeBackLayout);
        return container;
    }

    public void setDragEdge(SwipeBackLayout.DragEdge dragEdge) {
        if (null != mSwipeBackLayout) mSwipeBackLayout.setDragEdge(dragEdge);
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    protected void enableSwipe(boolean enable) {
        if (null != mSwipeBackLayout) mSwipeBackLayout.setEnablePullToBack(enable);
    }

    @Override
    public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
    }

}
