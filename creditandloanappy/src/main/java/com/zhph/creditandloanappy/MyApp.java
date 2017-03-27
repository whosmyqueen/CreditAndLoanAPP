package com.zhph.creditandloanappy;

import android.app.Application;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.zhph.commonlibrary.utils.CommonUtil;
import com.zhph.creditandloanappy.injector.components.AppComponent;
import com.zhph.creditandloanappu.injector.components.DaggerAppComponent;
import com.zhph.creditandloanappy.injector.modules.AppModule;

import javax.inject.Inject;

import cn.tongdun.android.shell.FMAgent;
import cn.tongdun.android.shell.exception.FMException;
import okhttp3.OkHttpClient;

/**
 * Created by whosmyqueen on 16/9/5.
 */
public class MyApp extends Application {
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
    public static final int MAX_DISK_CACHE_SIZE = 50 * ByteConstants.MB;
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 8;

    private static AppComponent mAppComponent;

    private static MyApp mMyApp;

    public static Application mContext;

    @Inject
    OkHttpClient mOkHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mMyApp = this;
        initComponent();
        initFrescoConfig();
        CommonUtil.init(this);
        try {
            FMAgent.init(this, FMAgent.ENV_SANDBOX);
        } catch (FMException e) {
            e.printStackTrace();
        }
    }

    public static MyApp getApplication() {
        return mMyApp;
    }

    private void initComponent() {
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        mAppComponent.inject(this);
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }

    private void initFrescoConfig() {
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(MAX_MEMORY_CACHE_SIZE, Integer.MAX_VALUE,
                MAX_MEMORY_CACHE_SIZE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this, mOkHttpClient)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig()).setBitmapMemoryCacheParamsSupplier(new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        }).setMainDiskCacheConfig(DiskCacheConfig.newBuilder(this).setMaxCacheSize(MAX_DISK_CACHE_SIZE).build())
                .setDownsampleEnabled(true).build();
        Fresco.initialize(this, config);
    }
}
