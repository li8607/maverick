package com.maverick.model;

import android.util.Log;

import com.maverick.MainApp;
import com.maverick.api.LeanCloudApi;
import com.maverick.api.LeanCloudApiInvokeProxy;
import com.maverick.api.YiYuanApi;
import com.maverick.api.YiYuanApiInvokeProxy;
import com.maverick.bean.GifInfoObj;
import com.maverick.bean.LeanCloudMusicResults;
import com.maverick.global.CachingControlInterceptor;
import com.maverick.global.UrlData;
import com.maverick.imodel.IJokeModel;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by limingfei on 2017/9/27.
 */
public class JokeModel implements IJokeModel {

    private String TAG = getClass().getSimpleName();
    private YiYuanApiInvokeProxy mJokeApiInvokeProxy;

    @Override
    public void release() {
        if (mJokeApiInvokeProxy != null) {
            mJokeApiInvokeProxy.cancelAll();
        }
    }

    @Override
    public void requestTextData(int page, int num, final OnTextResultListener listener) {

        if (listener == null) {
            return;
        }

        LeanCloudApi leanCloudApi = getRetrofit(UrlData.LEAN_CLOUD_BASE).create(LeanCloudApi.class);
        LeanCloudApiInvokeProxy proxy = new LeanCloudApiInvokeProxy(leanCloudApi);
        Call<LeanCloudMusicResults> musicList =  proxy.getMusicList(20, 0);
        musicList.enqueue(new Callback<LeanCloudMusicResults>() {
            @Override
            public void onResponse(Call<LeanCloudMusicResults> call, Response<LeanCloudMusicResults> response) {
                LeanCloudMusicResults leanCloudMusicResults = response.body();
                Log.e("lmf2", leanCloudMusicResults.toString());
            }

            @Override
            public void onFailure(Call<LeanCloudMusicResults> call, Throwable t) {
                Log.e("lmf2", "onFailure");
            }
        });

        YiYuanApi jokeApi = getRetrofit(UrlData.BASE).create(YiYuanApi.class);
        mJokeApiInvokeProxy = new YiYuanApiInvokeProxy(jokeApi);
        Call<GifInfoObj> call = mJokeApiInvokeProxy.getTextList(UrlData.APPID_VALUE, UrlData.SIGN_VALUE, page + "", num + "");
        call.enqueue(new Callback<GifInfoObj>() {
            @Override
            public void onResponse(Call<GifInfoObj> call, Response<GifInfoObj> response) {
                GifInfoObj gifInfoObj = response.body();
                if (gifInfoObj != null) {
                    listener.onSuccess(gifInfoObj);
                } else {
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<GifInfoObj> call, Throwable t) {
                listener.onFail();
            }
        });
    }

    @Override
    public void requestImgData(int page, int num, final OnImgResultListener listener) {

        if (listener == null) {
            return;
        }

        YiYuanApi jokeApi = getRetrofit(UrlData.BASE).create(YiYuanApi.class);
        mJokeApiInvokeProxy = new YiYuanApiInvokeProxy(jokeApi);
        Call<GifInfoObj> call = mJokeApiInvokeProxy.getImgList(UrlData.APPID_VALUE, UrlData.SIGN_VALUE, page + "", num + "");
        call.enqueue(new Callback<GifInfoObj>() {
            @Override
            public void onResponse(Call<GifInfoObj> call, Response<GifInfoObj> response) {
                GifInfoObj gifInfoObj = response.body();
                if (gifInfoObj != null) {
                    listener.onSuccess(gifInfoObj);
                } else {
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<GifInfoObj> call, Throwable t) {
                listener.onFail();
            }
        });
    }

    @Override
    public void requestGifData(int page, int num, final OnGifResultListener listener) {

        if (listener == null) {
            return;
        }

        YiYuanApi jokeApi = getRetrofit(UrlData.BASE).create(YiYuanApi.class);
        mJokeApiInvokeProxy = new YiYuanApiInvokeProxy(jokeApi);
        Call<GifInfoObj> call = mJokeApiInvokeProxy.getGifList(UrlData.APPID_VALUE, UrlData.SIGN_VALUE, page + "", num + "");
        call.enqueue(new Callback<GifInfoObj>() {
            @Override
            public void onResponse(Call<GifInfoObj> call, Response<GifInfoObj> response) {
                GifInfoObj gifInfoObj = response.body();
                if (gifInfoObj != null) {
                    listener.onSuccess(gifInfoObj);
                } else {
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<GifInfoObj> call, Throwable t) {
                listener.onFail();
            }
        });
    }

    private static Retrofit getRetrofit(String url) {
        //缓存容量
        long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MiB
        //缓存路径
        String cacheFile = MainApp.mContext.getCacheDir() + "/http";
        Cache cache = new Cache(new File(cacheFile), SIZE_OF_CACHE);
        //利用okhttp实现缓存
        OkHttpClient client = new OkHttpClient.Builder()
                //有网络时的拦截器
                .addNetworkInterceptor(CachingControlInterceptor.REWRITE_RESPONSE_INTERCEPTOR)
                //没网络时的拦截器
                .addInterceptor(CachingControlInterceptor.REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request()
//                                .newBuilder()
//                                .addHeader("X-LC-Id", "0C89a76qNodEmSHBpCSTGgCX-gzGzoHsz")
//                                .addHeader("X-LC-Sign", "2Bcw0fzmDjYF6WXqEJumCKxR," + System.currentTimeMillis())
//                                .addHeader("Content-Type", "application/json;charset=utf-8")
//                                .build();
//                        return chain.proceed(request);
//                    }
//
//                })
                .cache(cache)
                .build();
        //返回retrofit对象
        return new Retrofit.Builder().baseUrl(url)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
