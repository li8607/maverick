package com.maverick.model;

import com.maverick.MainApp;
import com.maverick.api.YiYuanApi;
import com.maverick.api.YiYuanApiInvokeProxy;
import com.maverick.bean.SisterInfo;
import com.maverick.bean.SisterInfoObj;
import com.maverick.global.CachingControlInterceptor;
import com.maverick.global.UrlData;
import com.maverick.imodel.ISisterModel;

import java.io.File;
import java.util.ArrayList;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterModel implements ISisterModel {

    private String TAG = getClass().getSimpleName();
    private YiYuanApiInvokeProxy mProxy;

    @Override
    public void release() {
        if (mProxy != null) {
            mProxy.cancelAll();
            mProxy = null;
        }
    }

    @Override
    public void requestData(String type, String title, int page, final OnResultListener listener) {
        if (listener == null) {
            return;
        }

        YiYuanApi sisterApi = getRetrofit(UrlData.BASE).create(YiYuanApi.class);
        mProxy = new YiYuanApiInvokeProxy(sisterApi);
        Call<SisterInfoObj> call = mProxy.getSister(UrlData.APPID_VALUE, UrlData.SIGN_VALUE, type, title, page + "");

        call.enqueue(new Callback<SisterInfoObj>() {
            @Override
            public void onResponse(Call<SisterInfoObj> call, Response<SisterInfoObj> response) {
                SisterInfoObj sisterInfoObj = response.body();
                if (sisterInfoObj != null && sisterInfoObj.getShowapi_res_body() != null && sisterInfoObj.getShowapi_res_body().getPagebean() != null && sisterInfoObj.getShowapi_res_body().getPagebean().getContentlist() != null && sisterInfoObj.getShowapi_res_body().getPagebean().getContentlist().size() > 0) {
                    listener.onSuccess(sisterInfoObj.getShowapi_res_body().getPagebean().getContentlist());
                } else {
                    listener.onSuccess(new ArrayList<SisterInfo>());
                }
            }

            @Override
            public void onFailure(Call<SisterInfoObj> call, Throwable t) {
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
