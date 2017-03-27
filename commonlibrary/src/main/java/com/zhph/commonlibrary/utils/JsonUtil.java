package com.zhph.commonlibrary.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Gson工具类
 * machao
 */
public class JsonUtil {

    /**
     * 把一个map变成json字符串
     *
     * @param map
     * @return
     */
    public static String parseMapToJson(Map<?, ?> map) {
        try {
            Gson gson = new Gson();
            return gson.toJson(map);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 把一个json字符串变成对象
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T parseJsonToBean(String json, Class<T> cls) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(json, cls);
        } catch (Exception e) {
        }
        return t;
    }

    /**
     * 把json字符串变成map
     *
     * @param json
     * @return
     */
    public static HashMap<String, Object> parseJsonToMap(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        HashMap<String, Object> map = null;
        try {
            map = gson.fromJson(json, type);
        } catch (Exception e) {
        }
        return map;
    }

    /**
     * 把json字符串变成集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static List<?> parseJsonToList(String json, Type type) {
        Gson gson = new Gson();
        List<?> list = gson.fromJson(json, type);
        return list;
    }

    /**
     * 获取json串中某个字段的值，注意，只能获取同一层级的value
     *
     * @param json
     * @param key
     * @return
     */
    public static String getFieldValue(String json, String key) {
        if (TextUtils.isEmpty(json))
            return null;
        if (!json.contains(key))
            return "";
        JSONObject jsonObject = null;
        String value = null;
        try {
            jsonObject = new JSONObject(json);
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 将一个字符串转换为json
     *
     * @param list
     * @return
     */
    public static String parseListToJson(List list) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String s = gson.toJson(list);
        return s;
    }

    /**
     * 将一个bean转换为json
     *
     * @param o
     * @return
     */
    public static String parseBeanToJson(Object o) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String s = gson.toJson(o);
        return s;
    }


    /**
     * 将一个bean转换为json
     *
     * @param o
     * @return
     */
    public static JSONObject parseBeanToJsonObject(Object o) throws JSONException {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String s = gson.toJson(o);
        return new JSONObject(s);
    }


    public static boolean copyE2H(JSONObject o1, JSONObject o2) throws JSONException {
        if (o1 == null || o2 == null || o1.length() == 0 || o2.length() == 0) {
            return false;
        }
        Iterator<String> keys = o2.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            o1.put(key, o2.get(key));
        }
        return true;
    }
}
