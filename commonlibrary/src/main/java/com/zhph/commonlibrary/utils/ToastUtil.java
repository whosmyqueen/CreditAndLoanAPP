package com.zhph.commonlibrary.utils;

import android.widget.Toast;


public class ToastUtil {
    private static Toast toast;

    /**
     * 强大的吐司，能够连续弹的吐司
     *
     * @param text
     */
    public static void showToast(String text) {
        if (toast == null) {
            //如果等于null，则创建
            toast = Toast.makeText(CommonUtil.mBaseContext, text, Toast.LENGTH_LONG);
        } else {
            //如果不等于空，则直接将text设置给toast
            toast.setText(text);
        }
        toast.show();
    }
}
