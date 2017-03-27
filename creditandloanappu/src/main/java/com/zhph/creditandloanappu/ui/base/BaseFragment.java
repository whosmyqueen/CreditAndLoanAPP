package com.zhph.creditandloanappu.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.zhph.commonlibrary.utils.CommonUtil;
import com.zhph.commonlibrary.views.editTextView.ClearEditText;
import com.zhph.creditandloanappu.injector.components.AppComponent;
import com.zhph.creditandloanappu.injector.components.FragmentComponent;
import com.zhph.creditandloanappu.saripaar.Verification;
import com.zhph.creditandloanappu.MyApp;
import com.zhph.creditandloanappu.R;
import com.zhph.creditandloanappu.injector.components.DaggerFragmentComponent;
import com.zhph.creditandloanappu.injector.modules.FragmentModule;
import com.zhph.commonlibrary.utils.ToastUtil;

import java.lang.reflect.Field;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by zwl on 16/9/30.
 */

public abstract class BaseFragment<T extends IPresenter> extends Fragment implements View.OnClickListener, Validator.ValidationListener {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    protected  BaseActivity mActivity;
    @Inject
    protected T mPresenter;
    protected FragmentComponent mFragmentComponent;
    protected KProgressHUD hud;
    ViewGroup view;
    public Validator validator;
    //是否可见状态
    private boolean isVisible;
    //View已经初始化完成
    private boolean isPrepared;
    //是否第一次加载完
    private boolean isFirstLoad = true;
    private LayoutInflater inflate;
    private ConnectivityManager manager;
    public T getPresenter() {
        return mPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isFirstLoad = true;
        //绑定View
        view = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        initValidator();
        isPrepared = true;
        this.inflate = inflater;
        setupActivityComponent(MyApp.getAppComponent(), new FragmentModule(this));
        initInjector();//dagger2注解,子类实现initInjector()方法 进行inject()
        if (mPresenter != null) {
            //绑定Presenter
            mPresenter.attachView(this, mActivity);
        }
        initDialog();
        //初始化事件和获取数据, 在此方法中获取数据不是懒加载模式
        initEventAndData();
        //在此方法中获取数据为懒加载模式,如不需要懒加载,请在initEventAndData获取数据,GankFragment有使用实例
        lazyLoad();
        //初始化弹框

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                processClick(v);
                break;
        }
    }

    protected abstract void processClick(View v);

    /**
     * 获取Component实例,方便子类使用
     */
    protected void setupActivityComponent(AppComponent appComponent, FragmentModule fragmentModule) {
        mFragmentComponent = DaggerFragmentComponent.builder().appComponent(appComponent).fragmentModule(fragmentModule).build();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public void onAttach(Activity context) {
        this.mActivity = (BaseActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) return;
        isFirstLoad = false;
        lazyLoadData();
    }


    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initEventAndData();

    protected abstract void lazyLoadData();

    protected void initDialog() {
            hud = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
    }

    public void showDialog() {
        if (hud == null) {
            initDialog();
        }
        hud.setLabel("玩命加载中").setCancellable(false).show();
    }

    public void showDialog(String loadingText) {
        if (hud == null) {
            initDialog();
        }
        hud.setLabel(loadingText).setCancellable(false).show();
    }

    public void dismissDialog() {
        if (hud == null) {
            initDialog();
        }
        hud.dismiss();
    }

    /**
     * 显示错误页面信息
     *
     * @param msg            错误信息
     * @param erroricon      错误图标
     * @param prompt         错误提示
     * @param titleIsVisible 是否显示标题栏
     * @param titleText      标题栏文字
     * @param titleBackBtn   标题栏返回按钮
     */
    public void onError(String msg, int erroricon, String prompt, boolean titleIsVisible, boolean titleBackBtn, String titleText) {
        view.removeAllViews();
        View views = inflate.inflate(R.layout.activity_error, null);
        ((ImageView) views.findViewById(R.id.error_icon)).setImageResource(erroricon);
        ((TextView) views.findViewById(R.id.error_msg)).setText(msg);
        ((TextView) views.findViewById(R.id.error_prompt)).setText(prompt);
        if (titleIsVisible) {
            (views.findViewById(R.id.titleBar)).setVisibility(View.VISIBLE);
            (views.findViewById(R.id.title_line)).setVisibility(View.VISIBLE);
            ((TextView) views.findViewById(R.id.title_text)).setText(titleText);
            if (titleBackBtn) {
                (views.findViewById(R.id.back1)).setVisibility(View.VISIBLE);
                (views.findViewById(R.id.back1)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            } else {
                (views.findViewById(R.id.back1)).setVisibility(View.GONE);
            }
        } else {
            (views.findViewById(R.id.titleBar)).setVisibility(View.GONE);
            (views.findViewById(R.id.title_line)).setVisibility(View.GONE);
        }
        view.addView(views);
    }

    /**
     * 显示成功页面
     */
    public void onSuccess(int layoutId) {
        view.removeAllViews();
        View views = inflate.inflate(layoutId, null);
        view.addView(views);
    }


    /**
     * 检测网络是否连接
     *
     * @return ture 为连接 false 为未连接
     */
    public boolean checkNetworkState() {
        boolean flag = false;
        //得到网络连接信息
        manager = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }

    /**
     * 网络未连接 或者 服务器错误的处理方法
     *
     * @param message 服务器错误的消息
     */
    public void toastNoNot(String message) {
        if (checkNetworkState()) {
            ToastUtil.showToast(message);
        } else {
            ToastUtil.showToast(getString(R.string.no_net));
        }
    }

    /**
     * 网络未连接 或者 服务器错误的处理方法
     *
     * @param error   网络未连接时候的消息
     * @param message 服务器错误的消息
     */
    public void toastNoNot(String error, String message) {
        if (checkNetworkState()) {
            ToastUtil.showToast(message);
        } else {
            ToastUtil.showToast(error);
        }
    }

    public void showLoadDialog() {
        showDialog();
    }

    public void showLoadDialog(String loadingText) {
        showDialog(loadingText);
    }

    public void setText(int id, CharSequence sequence) {
        View view = this.view.findViewById(id);
        if (view instanceof ClearEditText) {
            ((ClearEditText) view).setText(CommonUtil.trimString(sequence));
        } else if (view instanceof EditText) {
            ((EditText) view).setText(CommonUtil.trimString(sequence));
        } else if (view instanceof TextView) {
            ((TextView) view).setText(CommonUtil.trimString(sequence));
        }
    }

    public void setHint(int id, CharSequence sequence) {
        View view = this.view.findViewById(id);
        if (view instanceof ClearEditText) {
            ((ClearEditText) view).setHint(CommonUtil.trimString(sequence));
        } else if (view instanceof EditText) {
            ((EditText) view).setHint(CommonUtil.trimString(sequence));
        } else if (view instanceof TextView) {
            ((TextView) view).setHint(CommonUtil.trimString(sequence));
        }
    }

    public View getView(int id) {
        return view.findViewById(id);
    }

    public CharSequence getTextZ(int id) {
        View view = this.view.findViewById(id);
        if (view != null) {
            if (view instanceof ClearEditText) {
                return ((ClearEditText) view).getText().toString();
            } else if (view instanceof EditText) {
                return ((EditText) view).getText().toString();
            } else if (view instanceof TextView) {
                return ((TextView) view).getText().toString();
            }
        }
        return "";
    }

    public CharSequence getHint(int id) {
        View view = this.view.findViewById(id);
        if (view != null) {
            if (view instanceof ClearEditText) {
                return ((ClearEditText) view).getHint().toString();
            } else if (view instanceof EditText) {
                return ((EditText) view).getHint().toString();
            } else if (view instanceof TextView) {
                return ((TextView) view).getHint().toString();
            }
        }
        return "";
    }


    public void setChecked(int id, boolean checked) {
        View view = this.view.findViewById(id);
        if (view != null) {
            if (view instanceof CheckBox) {
                ((CheckBox) view).setChecked(checked);
            } else if (view instanceof Switch) {
                ((Switch) view).setChecked(checked);
            }
        }
    }

    public boolean getChecked(int id) {
        View view = this.view.findViewById(id);
        if (view != null) {
            if (view instanceof CheckBox) {
                return ((CheckBox) view).isChecked();
            } else if (view instanceof Switch) {
                return ((Switch) view).isChecked();
            }
        }
        return false;
    }

    private void initValidator() {
        Validator.registerAnnotation(Verification.class);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }


    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ToastUtil.showToast(errors.get(0).getCollatedErrorMessage(mActivity));
    }

    public void start2Activity(Intent intent) {
        startActivity(intent);
    }


    public void showMessage(String msg) {
        if (msg != null) {
            ToastUtil.showToast(msg);
        }
    }
}
