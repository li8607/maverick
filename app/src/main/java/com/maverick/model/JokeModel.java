package com.maverick.model;

import android.util.Log;

import com.maverick.api.JokeApi;
import com.maverick.api.JokeApiInvokeProxy;
import com.maverick.bean.GifInfoObj;
import com.maverick.global.UrlData;
import com.maverick.imodel.IJokeModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by limingfei on 2017/9/27.
 */
public class JokeModel implements IJokeModel {

    private String TAG = getClass().getSimpleName();
    private JokeApiInvokeProxy mJokeApiInvokeProxy;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlData.BASE)
                //增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JokeApi jokeApi = retrofit.create(JokeApi.class);
        mJokeApiInvokeProxy = new JokeApiInvokeProxy(jokeApi);
        Call<GifInfoObj> call = mJokeApiInvokeProxy.getTextList(UrlData.APPID_VALUE, UrlData.SIGN_VALUE, page + "", num + "");
        call.enqueue(new Callback<GifInfoObj>() {
            @Override
            public void onResponse(Call<GifInfoObj> call, Response<GifInfoObj> response) {
                GifInfoObj gifInfoObj = response.body();
                if (gifInfoObj != null) {
                    listener.onSuccess(gifInfoObj);
                    Log.e(TAG, "请求成功了");
                } else {
                    listener.onFail();
                    Log.e(TAG, "请求失败了");
                }
            }

            @Override
            public void onFailure(Call<GifInfoObj> call, Throwable t) {
                listener.onFail();
                Log.e(TAG, "请求失败了");
            }
        });
    }

    @Override
    public void requestImgData(int page, int num, final OnImgResultListener listener) {

        if (listener == null) {
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlData.BASE)
                //增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JokeApi jokeApi = retrofit.create(JokeApi.class);
        mJokeApiInvokeProxy = new JokeApiInvokeProxy(jokeApi);
        Call<GifInfoObj> call = mJokeApiInvokeProxy.getImgList(UrlData.APPID_VALUE, UrlData.SIGN_VALUE, page + "", num + "");
        call.enqueue(new Callback<GifInfoObj>() {
            @Override
            public void onResponse(Call<GifInfoObj> call, Response<GifInfoObj> response) {
                GifInfoObj gifInfoObj = response.body();
                if (gifInfoObj != null) {
                    listener.onSuccess(gifInfoObj);
                    Log.e(TAG, "请求成功了");
                } else {
                    listener.onFail();
                    Log.e(TAG, "请求失败了");
                }
            }

            @Override
            public void onFailure(Call<GifInfoObj> call, Throwable t) {
                listener.onFail();
                Log.e(TAG, "请求失败了");
            }
        });
    }

    @Override
    public void requestGifData(int page, int num, final OnGifResultListener listener) {

        if (listener == null) {
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlData.BASE)
                //增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JokeApi jokeApi = retrofit.create(JokeApi.class);
        mJokeApiInvokeProxy = new JokeApiInvokeProxy(jokeApi);
        Call<GifInfoObj> call = mJokeApiInvokeProxy.getGifList(UrlData.APPID_VALUE, UrlData.SIGN_VALUE, page + "", num + "");
        call.enqueue(new Callback<GifInfoObj>() {
            @Override
            public void onResponse(Call<GifInfoObj> call, Response<GifInfoObj> response) {
                GifInfoObj gifInfoObj = response.body();
                if (gifInfoObj != null) {
                    listener.onSuccess(gifInfoObj);
                    Log.e(TAG, "请求成功了");
                } else {
                    listener.onFail();
                    Log.e(TAG, "请求失败了");
                }
            }

            @Override
            public void onFailure(Call<GifInfoObj> call, Throwable t) {
                listener.onFail();
                Log.e(TAG, "请求失败了");
            }
        });
    }
}
