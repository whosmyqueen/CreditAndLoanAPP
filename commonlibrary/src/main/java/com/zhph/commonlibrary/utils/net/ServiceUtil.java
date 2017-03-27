package com.zhph.commonlibrary.utils.net;

import org.json.JSONObject;

/**
 * http请求类
 * Created by dell on 2016/3/21.
 */
public class ServiceUtil {

    public interface
    RequestCallBack<T> {
        void onSuccess(T result);

        void onFailure(String error);
    }

    /**
     * okGo post 请求
     * @param url
     * @param jsonObject
     * @param okGOStringCallBack
     */
    public static void okGoPost(String url, JSONObject jsonObject, OkGOStringCallBack okGOStringCallBack) {
        RestClient.okGoPostData(url, jsonObject, okGOStringCallBack);
    }
}
