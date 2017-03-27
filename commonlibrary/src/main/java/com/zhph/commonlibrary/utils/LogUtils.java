package com.zhph.commonlibrary.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * log工具类
 * machao
 */
public class LogUtils {

    public static final boolean ENABLE = true;
    private static final String TAG = "LogUtils";

    /**
     * 打印一个debug等级的 log
     */
    public static void d(String tag, String msg) {
        if (ENABLE) {
            Log.d("zamai:" + tag, msg);
        }
    }

    /**
     * 打印一个debug等级的 log
     */
    public static void e(String tag, String msg) {
        if (ENABLE) {
            Log.e("zamai:" + tag, msg);
        }
    }

    /**
     * 打印一个debug等级的 log
     */
    public static void e(Object obj, String msg) {
        if (ENABLE) {
            if (!TextUtils.isEmpty(msg)) {
                Log.e("zamai:" + obj.getClass().getSimpleName(), msg);
            }
        }
    }
}
