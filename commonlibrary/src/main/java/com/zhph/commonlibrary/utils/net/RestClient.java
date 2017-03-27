package com.zhph.commonlibrary.utils.net;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONObject;


/**
 * Created by dell on 2016/3/21.
 */
public class RestClient {


    /**
     * okGo post 请求
     *
     * @param url
     * @param params
     * @param okGOStringCallBack
     */
    public static void okGoPostData(String url, JSONObject params, OkGOStringCallBack okGOStringCallBack) {
        OkGo.post(url).params(new HttpParams("paramJson", params.toString())).connTimeOut(200000).readTimeOut(200000).writeTimeOut(200000).execute(okGOStringCallBack);
    }
}
