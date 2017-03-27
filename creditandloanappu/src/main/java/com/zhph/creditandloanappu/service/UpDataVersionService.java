package com.zhph.creditandloanappu.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.zhph.creditandloanappu.MyApp;
import com.zhph.creditandloanappu.R;
import com.zhph.commonlibrary.utils.LogUtils;
import com.zhph.commonlibrary.utils.ToastUtil;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：machao
 * APK 升级
 */
public class UpDataVersionService extends IntentService {
    private ConnectivityManager manager;

    // 定义下载目录： /mnt/sdcard/com.itheima.googleplay78/download
    public static String DOWNLOAD_DIR = Environment.getExternalStorageDirectory() + File.separator + MyApp.mContext.getPackageName() + File.separator + "download";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public UpDataVersionService() {
        super("UpDataVersionService");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String downAPKUrl = intent.getStringExtra("downAPKUrl");
        LogUtils.e(this, DOWNLOAD_DIR + " === DOWNLOAD_DIR");
        Intent broacdcastIntent = new Intent();
        broacdcastIntent.setAction("com.zhfq");


        OkGo.get(downAPKUrl).execute(new FileCallback() {
            @Override
            public void onSuccess(File file, Call call, Response response) {

                broacdcastIntent.putExtra("state", 1);
                UpDataVersionService.this.sendBroadcast(broacdcastIntent);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                getApplicationContext().startActivity(i);
                android.os.Process.killProcess(android.os.Process.myPid());
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (e.toString().contains("SocketTimeoutException")) {
                    toastNoNot(UpDataVersionService.this.getString(R.string.service_timeout));
                } else {
                    toastNoNot(UpDataVersionService.this.getString(R.string.service_error));
                }
                broacdcastIntent.putExtra("state", 0);
                UpDataVersionService.this.sendBroadcast(broacdcastIntent);
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                LogUtils.e(this, currentSize + "==currentSize" + totalSize + "==totalSize" + progress + "===progress" + networkSpeed + "==networkSpeed");

                Intent intent1 = new Intent("updateServiceApk");
                intent1.putExtra("progress", progress);
                sendBroadcast(intent1);
            }
        });


//        ServiceUtil.requestDownAPK(downAPKUrl, DOWNLOAD_DIR, "zhenghaofenqi.apk", new ServiceUtil.RequestCallBack<File>() {
//            @Override
//            public void onSuccess(File result) {
//
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.setDataAndType(Uri.fromFile(result),
//                        "application/vnd.android.package-archive");
//                ZhengHaoFenQIUApplication.mContext.startActivity(i);
//                android.os.Process.killProcess(android.os.Process.myPid());
//            }
//
//            @Override
//            public void onFailure(String error) {
//                ToastUtil.showToast("下载失败");
//            }
//        });
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 检测网络是否连接
     *
     * @return ture 为连接 false 为未连接
     */
    private boolean checkNetworkState() {
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
}
