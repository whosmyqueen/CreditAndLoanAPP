package com.zhph.commonlibrary.utils;

import android.app.Activity;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.type;


/**
 * Created by Administrator on 2016/10/13.
 */

public class GpsUtil {
    //声明AMapLocationClient类对象
    public static AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public static AMapLocationClientOption mLocationOption = null;

    /**
     * 向服务器发送当前的定位信息
     */


    //声明定位回调监听器
    public static AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            sendGPSInfo(aMapLocation);
        }
    };


    /**
     * 向服务器发送当前的定位信息
     */

    public static void sendGPSInfo(AMapLocation aMapLocation) {
        String address = "";
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //可在其中解析amapLocation获取相应内容。
                //                aMapLocation.getLatitude();//获取纬度
                //                aMapLocation.getLongitude();//获取经度
                //                aMapLocation.getAccuracy();//获取精度信息
                //                aMapLocation.getAddress();
                // 地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                //                aMapLocation.getProvince();//省信息
                //                aMapLocation.getCity();//城市信息
                //                aMapLocation.getDistrict();//城区信息
                //                aMapLocation.getStreet();//街道信息
                //                aMapLocation.getStreetNum();//街道门牌号信息
                //                aMapLocation.getAoiName();//获取当前定位点的AOI信息
                //                LogUtils.e("定位：" , aMapLocation.getAddress() + "\n经度:" + aMapLocation.getLongitude()
                //                        + "\n纬度：" + aMapLocation.getLatitude() + "\n精度信息：" + aMapLocation
                // .getAccuracy()
                //                        + "\n地址：" + aMapLocation.getProvince() + aMapLocation.getCity() +
                // aMapLocation.getDistrict()
                //                        + aMapLocation.getStreet() + aMapLocation.getStreetNum() + "\n当前定位点的AOI信息："
                // + aMapLocation.getAoiName());
                address = aMapLocation.getProvince() + aMapLocation.getCity() + aMapLocation.getDistrict() +
                        aMapLocation.getStreet() + aMapLocation.getStreetNum() + aMapLocation.getAoiName();
                if (address.isEmpty()) {
                    address = "暂无地址信息";
                }
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                LogUtils.e("定位：", "定位失败, 错误Code:" + aMapLocation.getErrorCode() + ",错误信息:" + aMapLocation
                        .getErrorInfo());
            }
        }
        JSONObject paramJson = new JSONObject();
        try {
            String custNo = CommonUtil.get4SP("custNo", "").toString();
            if (custNo == null || custNo.isEmpty()) {
                return;
            }
            paramJson.put("cust_no", custNo);
            paramJson.put("operation_type", type);
            paramJson.put("longitude", aMapLocation.getLongitude());
            paramJson.put("latitude", aMapLocation.getLatitude());
            paramJson.put("address", address);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.e("定位信息JSON", paramJson.toString());
        //        ServiceUtil.requestPostData(HttpUrl.saveGPSInfo, paramJson, new ServiceUtil.RequestCallBack<String>
        // () {
        //            @Override
        //            public void onSuccess(String result) {
        //                LogUtils.e(this, result);
        //            }
        //
        //            @Override
        //            public void onFailure(String error) {
        //                if (error == null) {
        //                    error = "当前GPS信号弱，定位失败了！";
        //                }
        //                LogUtils.e(this, error);
        //            }
        //        });
    }

    /**
     * 初始化gps参数
     */
    public static void initGPS(Activity activity) {
        //初始化定位
        mLocationClient = new AMapLocationClient(activity.getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)
        // 接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //AMapLocationClientOption.setProtocol是静态方法。
        //AMapLocationClientOption.AMapLocationProtocol提供良种枚举：
        //HTTP代表使用http请求，HTTPS代表使用https请求。
        //单个定位客户端生命周期内（调用AMapLocationClient.onDestroy()方法结束生命周期）设置一次即可。
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }


    /**
     * 初始化gps参数
     */
    public static void initGPS(Activity activity, AMapLocationListener aMapLocationListener) {
        //初始化定位
        mLocationClient = new AMapLocationClient(activity.getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(aMapLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)
        // 接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //AMapLocationClientOption.setProtocol是静态方法。
        //AMapLocationClientOption.AMapLocationProtocol提供良种枚举：
        //HTTP代表使用http请求，HTTPS代表使用https请求。
        //单个定位客户端生命周期内（调用AMapLocationClient.onDestroy()方法结束生命周期）设置一次即可。
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }


    public static void stopGPS() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }
    }

}
