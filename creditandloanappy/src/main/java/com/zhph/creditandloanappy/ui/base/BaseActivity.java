package com.zhph.creditandloanappy.ui.base;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.zhph.commonlibrary.utils.CommonUtil;
import com.zhph.commonlibrary.utils.StatusBarCompat;
import com.zhph.commonlibrary.views.editTextView.ClearEditText;
import com.zhph.creditandloanappy.injector.components.AppComponent;
import com.zhph.creditandloanappy.rxjava.RxManager;
import com.zhph.creditandloanappy.saripaar.Verification;
import com.zhph.creditandloanappy.AppManager;
import com.zhph.creditandloanappy.MyApp;
import com.zhph.creditandloanappu.R;
import com.zhph.creditandloanappy.injector.components.ActivityComponent;
import com.zhph.creditandloanappu.injector.components.DaggerActivityComponent;
import com.zhph.creditandloanappy.injector.modules.ActivityModule;
import com.zhph.commonlibrary.utils.ToastUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by whosmyqueen on 16/9/30.
 */
public abstract class BaseActivity<T extends IPresenter> extends SwipeBackAppCompatActivity implements View.OnClickListener, Validator
        .ValidationListener {
    @Inject
    public T mPresenter;
    public RxManager mRxManager;
    public KProgressHUD hud;
    public Validator validator;
    protected ActivityComponent mActivityComponent;
    private ConnectivityManager manager;

    public T getPresenter() {
        return mPresenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxManager = new RxManager();
        setBaseConfig();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initValidator();
        setupActivityComponent(MyApp.getAppComponent(), new ActivityModule(this));
        initInjector();
        if (mPresenter != null) mPresenter.attachView(this, this);
        View view = findViewById(R.id.back1);
        if (view != null) {
            view.setOnClickListener(this);
        }
        initDialog();

        initEventAndData();

    }

    private void setBaseConfig() {
        initTheme();
        AppManager.getAppManager().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SetStatusBarColor();
    }

    /**
     * 获取Component实例,方便子类使用
     */
    protected void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        mActivityComponent = DaggerActivityComponent.builder().appComponent(appComponent).build();
    }

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initEventAndData();

    private void initTheme() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back1:
                super.finish();
                break;
            default:
                processClick(v);
                break;
        }
    }


    /**
     * BaseActivity没有处理的点击事件，在此方法处理
     */
    protected abstract void processClick(View v);

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.red_e60012));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void setTranslateBar() {
        StatusBarCompat.translucentStatusBar(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
        mRxManager.clear();
        ButterKnife.unbind(this);
        AppManager.getAppManager().finishActivity(this);
    }

    protected void initDialog() {
        hud = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
    }

    public void showDialog() {
        if (hud == null) {
            initDialog();
        }
        try {
            hud.setLabel("玩命加载中").setCancellable(false).show();
        } catch (Exception e) {

        }
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

    public void start2Activity(Intent intent) {
        startActivity(intent);
    }


    public void showMessage(String msg) {
        if (msg != null) {
            ToastUtil.showToast(msg);
        }
    }

    /**
     * 显示错误页面信息
     *
     * @param msg            错误信息
     * @param erroricon      错误图标
     * @param prompt         错误提示
     * @param titleIsVisible 是否显示标题栏
     * @param titleText      标题栏文字
     */
    public void onError(String msg, int erroricon, String prompt, boolean titleIsVisible, String titleText) {
        setContentView(R.layout.activity_error);
        ((ImageView) findViewById(R.id.error_icon)).setImageResource(erroricon);
        ((TextView) findViewById(R.id.error_msg)).setText(msg);
        ((TextView) findViewById(R.id.error_prompt)).setText(prompt);
        if (titleIsVisible) {
            (findViewById(R.id.titleBar)).setVisibility(View.VISIBLE);
            (findViewById(R.id.title_line)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.title_text)).setText(titleText);
            View view = findViewById(R.id.back1);
            if (view != null) {
                view.setOnClickListener(this);
            }
        } else {
            (findViewById(R.id.titleBar)).setVisibility(View.GONE);
            (findViewById(R.id.title_line)).setVisibility(View.GONE);
        }
    }


    /**
     * 检测网络是否连接
     *
     * @return ture 为连接 false 为未连接
     */
    protected boolean checkNetworkState() {
        boolean flag = false;
        //得到网络连接信息
        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
        View view = findViewById(id);
        if (view instanceof ClearEditText) {
            ((ClearEditText) view).setText(CommonUtil.trimEmptyString(sequence));
        } else if (view instanceof EditText) {
            ((EditText) view).setText(CommonUtil.trimEmptyString(sequence));
        } else if (view instanceof TextView) {
            ((TextView) view).setText(CommonUtil.trimEmptyString(sequence));
        }
    }

    public void setHint(int id, CharSequence sequence) {
        View view = findViewById(id);
        if (view instanceof ClearEditText) {
            ((ClearEditText) view).setHint(CommonUtil.trimEmptyString(sequence));
        } else if (view instanceof EditText) {
            ((EditText) view).setHint(CommonUtil.trimEmptyString(sequence));
        } else if (view instanceof TextView) {
            ((TextView) view).setHint(CommonUtil.trimEmptyString(sequence));
        }
    }

    public View getView(int id) {
        return findViewById(id);
    }

    public CharSequence getTextZ(int id) {
        View view = findViewById(id);
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
        View view = findViewById(id);
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
        View view = findViewById(id);
        if (view != null) {
            if (view instanceof CheckBox) {
                ((CheckBox) view).setChecked(checked);
            } else if (view instanceof Switch) {
                ((Switch) view).setChecked(checked);
            }
        }
    }

    public boolean getChecked(int id) {
        View view = findViewById(id);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ToastUtil.showToast(errors.get(0).getCollatedErrorMessage(this));
    }

}
