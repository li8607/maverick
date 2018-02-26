package com.maverick.model;

import android.text.TextUtils;

import com.maverick.MainApp;
import com.maverick.api.YiYuanApi;
import com.maverick.api.YiYuanApiInvokeProxy;
import com.maverick.bean.CaricatureInfo;
import com.maverick.bean.CaricatureInfoObj;
import com.maverick.bean.CaricatureListInfoObj;
import com.maverick.global.CachingControlInterceptor;
import com.maverick.global.UrlData;
import com.maverick.imodel.ICaricatureModel;

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
 * Created by limingfei on 2017/10/20.
 */
public class CaricatureModel implements ICaricatureModel {

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
    public void requestData(String type, int page, final OnResultListener listener) {
        if (listener == null) {
            return;
        }

        YiYuanApi api = getRetrofit(UrlData.BASE).create(YiYuanApi.class);
        mProxy = new YiYuanApiInvokeProxy(api);
        Call<CaricatureInfoObj> call = mProxy.getCaricature(UrlData.APPID_VALUE, UrlData.SIGN_VALUE, type, page + "");

        call.enqueue(new Callback<CaricatureInfoObj>() {
            @Override
            public void onResponse(Call<CaricatureInfoObj> call, Response<CaricatureInfoObj> response) {
                CaricatureInfoObj info = response.body();

                if (info == null || !TextUtils.equals(info.getShowapi_res_code(), "0")) {
                    listener.onFail();
                    return;
                }

                if (info.getShowapi_res_body() != null && info.getShowapi_res_body().getPagebean() != null && info.getShowapi_res_body().getPagebean().getContentlist() != null && info.getShowapi_res_body().getPagebean().getContentlist().size() > 0) {
                    listener.onSuccess(info.getShowapi_res_body().getPagebean().getContentlist(), info.getShowapi_res_body().getPagebean().isHasMorePage());
                } else {
                    listener.onSuccess(new ArrayList<CaricatureInfo>(), false);
                }
            }

            @Override
            public void onFailure(Call<CaricatureInfoObj> call, Throwable t) {
                listener.onFail();
            }
        });
    }

    @Override
    public void requestDetailData(String id, final OnDetailResultListener listener) {
        if (listener == null) {
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlData.BASE)
                //增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YiYuanApi api = retrofit.create(YiYuanApi.class);
        mProxy = new YiYuanApiInvokeProxy(api);
        Call<CaricatureListInfoObj> call = mProxy.getCaricatureDetail(UrlData.APPID_VALUE, UrlData.SIGN_VALUE, id);

        call.enqueue(new Callback<CaricatureListInfoObj>() {
            @Override
            public void onResponse(Call<CaricatureListInfoObj> call, Response<CaricatureListInfoObj> response) {
                CaricatureListInfoObj info = response.body();

                if (info == null || !TextUtils.equals(info.getShowapi_res_code(), "0")) {
                    listener.onFail();
                    return;
                }

                if (info.getShowapi_res_body() != null && info.getShowapi_res_body().getItem() != null) {
                    listener.onSuccess(info.getShowapi_res_body().getItem());
                } else {
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<CaricatureListInfoObj> call, Throwable t) {
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
