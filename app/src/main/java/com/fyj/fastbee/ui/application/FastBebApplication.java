package com.fyj.fastbee.ui.application;

import android.content.Context;
import android.content.res.Resources;

import com.fyj.easylinkingimageloader.ImageLoaderHelper;
import com.fyj.easylinkingnet.core.OkHttpUtils;
import com.fyj.easylinkingnet.core.log.LoggerInterceptor;
import com.fyj.easylinkingutils.utils.ImageCompressUtils;
import com.fyj.easylinkingutils.utils.SPUtils;
import com.fyj.easylinkingutils.utils.ScreenUtils;
import com.fyj.easylinkingutils.utils.ToastUtil;
import com.fyj.easylinkingutils.utils.XLog;
import com.fyj.easylinkingview.swipbackview.BaseApplication;
import com.fyj.fastbee.BuildConfig;
import com.fyj.fastbee.db.DBCipherHelper;
import com.fyj.fastbee.db.DatabaseManager;
import com.fyj.fastbee.globel.CachePath;
import com.fyj.fastbee.http.RetrofitHelper;
import com.fyj.fastbee.imageloaderutils.UILImageLoader;
import com.fyj.fastbee.utils.CrashHandler;
import com.squareup.leakcanary.LeakCanary;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * 描述: <br>
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数:
 */
public class FastBebApplication extends BaseApplication {

    private static final String EMOTION_KEYBOARD = "EmotionKeyboard";
    private static final String SOFT_INPUT_HEIGHT = "soft_input_height";

    private static SoftReference<Context> fbContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initSoftReference();
        initCrashView();
        initRetrofit();
        initImageHelper();
        initToastUtils();
        initHttpHelper();
        initDBManager();
        initLogUtils();
        initCachepath();
        initKeyBoard();
        LeakCanary.install(this);
    }

    private void initRetrofit() {
        RetrofitHelper.init();
    }

    private void initCrashView() {
        CrashHandler.getInstance().init(this, this.getPackageName());
    }

    private void initSoftReference() {
        fbContext = new SoftReference<Context>(this);
    }

    public static SoftReference<Context> getAppContext() {
        return fbContext;
    }

    private void initKeyBoard() {
        int height = (int) (ScreenUtils.getScreenHeight(this) * 0.4333);
        SPUtils.putInt(this, EMOTION_KEYBOARD, SOFT_INPUT_HEIGHT, height);
    }

    private void initCachepath() {
        CachePath.initDirName("FastBee");
        ImageCompressUtils.init(new File(CachePath.getImageCompressCachePath(this)));
    }

    /**
     * 初始化Log工具类
     * 自动判断打包模式
     * 打开/关闭Log
     */
    private void initLogUtils() {
        if (BuildConfig.LOG_DEBUG) {
            XLog.openLog();
        } else {
            XLog.closeLog();
        }
    }

    /**
     * 初始化数据库操作类
     */
    private void initDBManager() {
        DatabaseManager.initializeInstance(new DBCipherHelper(this));
    }

    /**
     * 初始化请求框架
     */
    private void initHttpHelper() {
        try {
//            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(new InputStream[]{getAssets().open("elinkpay.crt")}, null, null);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                    .readTimeout(10000L, TimeUnit.MILLISECONDS)
                    .addInterceptor(new LoggerInterceptor("FastBebApp", true))
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
//                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .build();
            OkHttpUtils.initClient(okHttpClient);

        } catch (Exception e) {
            XLog.e("FastBebApp", "initOKHttp", e);
        }
    }

    /**
     * 初始化吐司工具类
     */
    private void initToastUtils() {
        ToastUtil.init(fbContext.get());
    }

    /**
     * 初始化图片加载框架
     */
    private void initImageHelper() {
        ImageLoaderHelper.initApplication(fbContext.get(), new UILImageLoader());

    }

    /**
     * 获得strings.xml中的值
     *
     * @param id id
     * @return string
     */
    public static String readString(int id) {
        return fbContext.get().getString(id);
    }

    /**
     * 获得资源类
     *
     * @return res
     */
    public static Resources getResourse() {
        return fbContext.get().getResources();
    }


}
