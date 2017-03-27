package com.zhph.creditandloanappu.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhph.creditandloanappu.R;


/**
 * 用来管理界面加载的逻辑
 *
 * @author machao
 */
public abstract class LoadingPage extends FrameLayout {

    public Context mContext;

    //生成父类构造：alt+shift+s->c
    public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLoadingPage();
        this.mContext = context;
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(Context context) {
        this(context, null);
    }

    //定义3种状态常量
    public static final int STATE_LOADING = 0;//加载中的状态
    public static final int STATE_ERROR = 1;//加载失败的状态
    public static final int STATE_SUCCESS = 2;//加载成功的状态

    public int mState = STATE_LOADING;//表示当前界面的状态，默认是加载中
    private View loadingView;//加载中的View
    private View errorView;//加载失败的View
    private View successView;//加载成功的View


    //    private Handler mHandler = new Handler(){
    //        @Override
    //        public void handleMessage(Message msg) {
    //            if (msg.what == 0){
    //                mState = STATE_ERROR;
    //                showPage();
    //            }
    //        }
    //    };

    /**
     * 初始化LoadingPage
     */
    private void initLoadingPage() {
        //1.往LoadingPage总添加3个状态对应的view对象
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (loadingView == null) {
            loadingView = View.inflate(getContext(), R.layout.page_loading, null);
            //            if (loadingView.getVisibility() == View.VISIBLE){
            //                mHandler.sendEmptyMessageDelayed(0,5000);
            //            }

        }
        addView(loadingView, params);

        if (errorView == null) {
            errorView = View.inflate(getContext(), R.layout.page_error, null);
            TextView tv_error_title = (TextView) errorView.findViewById(R.id.tv_error_title);

            ImageView iv_error_return = (ImageView) errorView.findViewById(R.id.iv_error_return);

            setErrorTitle(tv_error_title, iv_error_return);

        }
        addView(errorView, params);

        if (successView == null) {
            successView = createSuccessView();
        }
        if (successView != null) {
            addView(successView, params);
        } else {
            //说明createSuccessView()没有实现
            throw new IllegalArgumentException("The method createSuccessView() can not return null!");
        }

        //2.根据默认的状态显示默认的View
        showPage();

        //3.加载数据，然后根据拿到的数据刷新界面
        //        loadDataAndRefreshPage();
    }

    /**
     * 设置错误页的标题
     */
    protected void setErrorTitle(TextView tv_error_title, ImageView back) {

    }

    /**
     * 根据当前的state，让对应的View显示
     */
    public void showPage() {
        //1.先隐藏所有的View
        loadingView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.INVISIBLE);
        successView.setVisibility(View.INVISIBLE);
        //2.谁的状态谁显示
        switch (mState) {
            case STATE_LOADING://loading的状态
                loadingView.setVisibility(View.VISIBLE);
                break;
            case STATE_ERROR://加载失败的状态
                errorView.setVisibility(View.VISIBLE);
                break;
            case STATE_SUCCESS://加载成功的状态
                successView.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 判断数据是否为空
     *
     * @param data
     * @return
     */
    private int checkData(Object data) {
        return data == null ? STATE_ERROR : STATE_SUCCESS;
    }

    /**
     * 由于每个界面的successView都不一样，那么应该由每个界面去实现
     *
     * @return
     */
    public abstract View createSuccessView();

    /**
     * 由于只关心每个界面加载到的数据是否为空，不关心具体的加载过程，所以抽象出来，每个界面
     * 去具体实现，然后返回自己界面的数据就行了
     * 返回null 则为网络错误界面，返回任意对象则为访问成功
     *
     * @return
     */
    //    public abstract Object loadData();


}
