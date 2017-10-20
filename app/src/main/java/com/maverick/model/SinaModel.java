package com.maverick.model;

import android.text.TextUtils;
import android.util.Log;

import com.maverick.api.YiYuanApi;
import com.maverick.api.YiYuanApiInvokeProxy;
import com.maverick.bean.SinaInfo;
import com.maverick.bean.SinaInfoObj;
import com.maverick.global.UrlData;
import com.maverick.imodel.ICaricatureModel;
import com.maverick.imodel.ISinaModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by limingfei on 2017/10/20.
 */
public class SinaModel implements ISinaModel {

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
    public void requestData(String type, String space, int page, final OnResultListener listener) {
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
        Call<SinaInfoObj> call = mProxy.getSina(UrlData.APPID_VALUE, UrlData.SIGN_VALUE, type, space, "", "", page + "");

        call.enqueue(new Callback<SinaInfoObj>() {
            @Override
            public void onResponse(Call<SinaInfoObj> call, Response<SinaInfoObj> response) {
                SinaInfoObj info = response.body();

                if (info == null || !TextUtils.equals(info.getShowapi_res_code(), "0")) {
                    Log.e(TAG, "请求失败了");
                    listener.onFail();
                    return;
                }

                if (info.getShowapi_res_body() != null && info.getShowapi_res_body().getPagebean() != null && info.getShowapi_res_body().getPagebean().getContentlist() != null && info.getShowapi_res_body().getPagebean().getContentlist().size() > 0) {
                    listener.onSuccess(info.getShowapi_res_body().getPagebean().getContentlist(), info.getShowapi_res_body().getPagebean().isHasMorePage());
                    Log.e(TAG, "请求了" + info.getShowapi_res_body().getPagebean().getContentlist().size() + "条数据");
                } else {
                    listener.onSuccess(new ArrayList<SinaInfo>(), false);
                    Log.e(TAG, "请求了" + 0 + "条数据");
                }
            }

            @Override
            public void onFailure(Call<SinaInfoObj> call, Throwable t) {
                listener.onFail();
                Log.e(TAG, "请求失败了");
            }
        });
    }
}
