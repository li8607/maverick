package com.maverick.model;

import android.util.Log;

import com.maverick.api.BeautyApi;
import com.maverick.api.BeautyApiInvokeProxy;
import com.maverick.bean.BeautyInfo;
import com.maverick.bean.BeautyItemInfo;
import com.maverick.imodel.IBeautyModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by limingfei on 2017/9/26.
 */
public class BeautyModel implements IBeautyModel {

    private String TAG = getClass().getSimpleName();
    private BeautyApiInvokeProxy mBeautyApiInvokeProxy;

    @Override
    public void release() {
        if (mBeautyApiInvokeProxy != null) {
            mBeautyApiInvokeProxy.cancelAll();
            mBeautyApiInvokeProxy = null;
        }
    }

    @Override
    public void requestData(int page, int num, final OnResultListener listener) {

        if (listener == null) {
            return;
        }

        String url = "http://gank.io/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                //增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BeautyApi beautyApi = retrofit.create(BeautyApi.class);
        mBeautyApiInvokeProxy = new BeautyApiInvokeProxy(beautyApi);
        Call<BeautyInfo> call = mBeautyApiInvokeProxy.getBeautyInfo(num, page);
        call.enqueue(new Callback<BeautyInfo>() {
                         @Override
                         public void onResponse(Call<BeautyInfo> call, Response<BeautyInfo> response) {
                             BeautyInfo beautyInfo = response.body();
                             if (beautyInfo != null) {
                                 listener.onSuccess(beautyInfo.getResults());
                                 Log.e(TAG, "请求了" + beautyInfo.getResults().size() + "条数据");
                             } else {
                                 listener.onSuccess(new ArrayList<BeautyItemInfo>());
                                 Log.e(TAG, "请求了" + 0 + "条数据");
                             }
                         }

                         @Override
                         public void onFailure(Call<BeautyInfo> call, Throwable t) {
                             listener.onFail();
                             Log.e(TAG, "请求失败了");
                         }
                     }
        );
    }
}
