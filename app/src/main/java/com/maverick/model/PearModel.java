package com.maverick.model;

import com.maverick.api.PearApi;
import com.maverick.api.PearApiInvokeProxy;
import com.maverick.bean.PearVideoInfoObj;
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
                listener.onFail();
            }
        });
    }
}
