package com.maverick.model;

import android.text.TextUtils;

import com.maverick.api.PearApi;
import com.maverick.api.PearApiInvokeProxy;
import com.maverick.bean.PearVideoDetailInfoData;
import com.maverick.bean.PearVideoInfoObj;
import com.maverick.bean.PearVideoTabDetailInfo;
import com.maverick.global.UrlData;
import com.maverick.hepler.ClientHelper;
import com.maverick.imodel.IPearModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/10/26.
 */

public class PearModel implements IPearModel {

    private PearApiInvokeProxy mProxy;

    @Override
    public void release() {
        if (mProxy != null) {
            mProxy.cancelAll();
            mProxy = null;
        }
    }

    @Override
    public void requestData(final OnResultListener listener) {

        if (listener == null) {
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlData.PEAR_BASE)
                //增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())
                .client(ClientHelper.genericClientPear())//添加头文件
                .build();

        PearApi api = retrofit.create(PearApi.class);
        mProxy = new PearApiInvokeProxy(api);
        Call<PearVideoInfoObj> call = mProxy.getPearTabList();

        call.enqueue(new Callback<PearVideoInfoObj>() {
            @Override
            public void onResponse(Call<PearVideoInfoObj> call, Response<PearVideoInfoObj> response) {
                if (response.body() == null) {
                    listener.onFail();
                    return;
                }

                listener.onSuccess(response.body().getCategoryList());
            }

            @Override
            public void onFailure(Call<PearVideoInfoObj> call, Throwable t) {
                if (!call.isCanceled()) {
                    listener.onFail();
                }
            }
        });
    }

    @Override
    public void requestTabItemData(int hotPageidx, String categoryId, final OnTabItemResultListener listener) {

        if (listener == null) {
            return;
        }

        if (TextUtils.isEmpty(categoryId)) {
            listener.onFail();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlData.PEAR_BASE)
                //增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())
                .client(ClientHelper.genericClientPear())//添加头文件
                .build();

        PearApi api = retrofit.create(PearApi.class);
        mProxy = new PearApiInvokeProxy(api);
        Call<PearVideoTabDetailInfo> call = mProxy.getPearTabDetail(hotPageidx + "", categoryId);

        call.enqueue(new Callback<PearVideoTabDetailInfo>() {
            @Override
            public void onResponse(Call<PearVideoTabDetailInfo> call, Response<PearVideoTabDetailInfo> response) {
                if (response.body() == null) {
                    listener.onFail();
                    return;
                }

                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<PearVideoTabDetailInfo> call, Throwable t) {
                if (!call.isCanceled()) {
                    listener.onFail();
                }
            }
        });
    }

    @Override
    public void requestTabItemNextData(String url, final OnTabItemResultListener listener) {

        if (listener == null) {
            return;
        }

        if (TextUtils.isEmpty(url)) {
            listener.onFail();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlData.PEAR_BASE)
                //增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())
                .client(ClientHelper.genericClientPear())//添加头文件
                .build();

        PearApi api = retrofit.create(PearApi.class);
        mProxy = new PearApiInvokeProxy(api);
        Call<PearVideoTabDetailInfo> call = mProxy.getPearTabNextDetail(url);

        call.enqueue(new Callback<PearVideoTabDetailInfo>() {
            @Override
            public void onResponse(Call<PearVideoTabDetailInfo> call, Response<PearVideoTabDetailInfo> response) {
                if (response.body() == null) {
                    listener.onFail();
                    return;
                }

                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<PearVideoTabDetailInfo> call, Throwable t) {
                if (!call.isCanceled()) {
                    listener.onFail();
                }
            }
        });
    }

    @Override
    public void requestPearDetail(String contId, final OnPearDetailListener listener) {

        if (listener == null) {
            return;
        }

        if (TextUtils.isEmpty(contId)) {
            listener.onFail();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlData.PEAR_BASE)
                //增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())
                .client(ClientHelper.genericClientPear())//添加头文件
                .build();

        PearApi api = retrofit.create(PearApi.class);
        mProxy = new PearApiInvokeProxy(api);
        Call<PearVideoDetailInfoData> call = mProxy.getPearDetail(contId);

        call.enqueue(new Callback<PearVideoDetailInfoData>() {
            @Override
            public void onResponse(Call<PearVideoDetailInfoData> call, Response<PearVideoDetailInfoData> response) {
                if (response.body() == null) {
                    listener.onFail();
                    return;
                }

                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<PearVideoDetailInfoData> call, Throwable t) {
                if (!call.isCanceled()) {
                    listener.onFail();
                }
            }
        });
    }
}
