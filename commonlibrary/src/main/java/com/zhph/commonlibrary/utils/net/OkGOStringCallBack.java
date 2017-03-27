package com.zhph.commonlibrary.utils.net;


import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Admin on 2016/10/20.
 */

public abstract class OkGOStringCallBack extends StringCallback {
    @Override
    public void onSuccess(String s, Call call, Response response) {
        onResult(s, call, response);
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        onFailure(call, response, e);
    }


    @Override
    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
        inProgress(currentSize, totalSize, progress, networkSpeed);
    }


    /**
     * 外部调用此方法进行数据处理
     *
     * @param call
     */
    public abstract void onResult(String s, Call call, Response response);

    public abstract void onFailure(Call call, Response response, Exception e);

    protected abstract void inProgress(long currentSize, long totalSize, float progress, long networkSpeed);

}
