package com.fyj.fastbee.http;

import com.fyj.easylinkingnet.core.log.LoggerInterceptor;
import com.fyj.easylinkingutils.utils.NetworkUtils;
import com.fyj.fastbee.BuildConfig;
import com.fyj.fastbee.bean.YueProjectBean;
import com.fyj.fastbee.bean.base.BaseObjectBean;
import com.fyj.fastbee.globel.CachePath;
import com.fyj.fastbee.ui.application.FastBebApplication;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/21<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class RetrofitHelper {

    private static OkHttpClient okHttpClient = null;
    private static FriendsApis friendsService = null;

    public static void init() {
        initOkHttp();
        friendsService = getFriendsApiService();
    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.LOG_DEBUG) {
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
//            builder.addInterceptor(loggingInterceptor);
            builder.addInterceptor(new LoggerInterceptor("FastBebApp", true));
        }
        File cacheFile = CachePath.getRetrofitCachePath(FastBebApplication.getAppContext().get());
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isConnected(FastBebApplication.getAppContext().get())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtils.isConnected(FastBebApplication.getAppContext().get())) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
//        Interceptor apikey = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                request = request.newBuilder()
//                        .addHeader("apikey",Constants.KEY_API)
//                        .build();
//                return chain.proceed(request);
//            }
//        };
//        builder.addInterceptor(apikey);
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    private static FriendsApis getFriendsApiService() {
        Retrofit friendsRetrofit = new Retrofit.Builder()
                .baseUrl(FriendsApis.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return friendsRetrofit.create(FriendsApis.class);
    }


    public static Observable<BaseObjectBean<YueProjectBean>> getActivityList(String refUserId, String channel, String param, String updatedAt) {
        return friendsService.getActivityList(refUserId, channel, param, updatedAt);
    }


}
