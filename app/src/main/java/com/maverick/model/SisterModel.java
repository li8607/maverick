package com.maverick.model;

import android.util.Log;

import com.maverick.api.SisterApi;
import com.maverick.api.SisterApiInvokeProxy;
import com.maverick.bean.SisterInfo;
import com.maverick.bean.SisterInfoObj;
import com.maverick.global.UrlData;
import com.maverick.imodel.ISisterModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterModel implements ISisterModel {

    private String TAG = getClass().getSimpleName();
    private SisterApiInvokeProxy mProxy;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlData.BASE)
                //增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SisterApi sisterApi = retrofit.create(SisterApi.class);
        mProxy = new SisterApiInvokeProxy(sisterApi);
        Call<SisterInfoObj> call = mProxy.getSisterList(UrlData.APPID_VALUE, UrlData.SIGN_VALUE, type, title, page + "");

        call.enqueue(new Callback<SisterInfoObj>() {
            @Override
            public void onResponse(Call<SisterInfoObj> call, Response<SisterInfoObj> response) {
                SisterInfoObj sisterInfoObj = response.body();
                Log.e(TAG, "sisterInfoObj = " + sisterInfoObj);
                if (sisterInfoObj != null && sisterInfoObj.getShowapi_res_body() != null && sisterInfoObj.getShowapi_res_body().getPagebean() != null && sisterInfoObj.getShowapi_res_body().getPagebean().getContentlist() != null && sisterInfoObj.getShowapi_res_body().getPagebean().getContentlist().size() > 0) {
                    listener.onSuccess(sisterInfoObj.getShowapi_res_body().getPagebean().getContentlist());
                    Log.e(TAG, "请求了" + sisterInfoObj.getShowapi_res_body().getPagebean().getContentlist().size() + "条数据");
                } else {
                    listener.onSuccess(new ArrayList<SisterInfo>());
                    Log.e(TAG, "请求了" + 0 + "条数据");
                }
            }

            @Override
            public void onFailure(Call<SisterInfoObj> call, Throwable t) {
                listener.onFail();
                Log.e(TAG, "请求失败了");
            }
        });

    }
}
