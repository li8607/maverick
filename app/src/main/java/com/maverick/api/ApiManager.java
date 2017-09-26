package com.maverick.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.maverick.MainApp;
import com.maverick.global.UrlData;
import com.maverick.util.NetWorkUtil;

import java.io.File;
import java.io.IOException;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ll on 2017/5/22.
 */
public class ApiManager {

    private ApiManager() {

    }

    private static ApiManager mApiManager;

    public static ApiManager newInstance() {

        if (mApiManager == null) {
            synchronized (ApiManager.class) {
                if (mApiManager == null) {
                    mApiManager = new ApiManager();
                }
            }
        }

        return mApiManager;
    }

    private ImgApi mImgApi;
    private Object mObject = new Object();

    private static File httpCacheDirectory = new File(MainApp.mContext.getCacheDir(), "zhihuCache");
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);


    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetWorkUtil.isNetWorkAvailable(MainApp.mContext)) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();



    public ImgApi getImgApi() {

        if(mImgApi == null) {

           synchronized (mObject) {
                mImgApi = new Retrofit.Builder()
                        .baseUrl(UrlData.BASE)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(ImgApi.class);
           }

        }

        return mImgApi;
    }

    public ImgApi getBeautyApi() {

        if(mImgApi == null) {

            synchronized (mObject) {
                mImgApi = new Retrofit.Builder()
                        .baseUrl(UrlData.BEAUTY_BASE)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(ImgApi.class);
            }

        }

        return mImgApi;
    }

}
