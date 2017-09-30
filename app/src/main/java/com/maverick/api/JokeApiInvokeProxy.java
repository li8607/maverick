package com.maverick.api;

import com.maverick.bean.GifInfoObj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;

/**
 * Created by limingfei on 2017/9/27.
 */
public class JokeApiInvokeProxy {

    private JokeApi mJokeApi;

    private List<Call> mCallList = Collections.synchronizedList(new ArrayList<Call>());

    public JokeApiInvokeProxy(JokeApi jokeApi) {
        this.mJokeApi = jokeApi;
    }


    public Call<GifInfoObj> getTextList(String appid, String sign,String page, String maxResult) {
        Call<GifInfoObj> call = mJokeApi.getTextList(appid, sign, page, maxResult);
        mCallList.add(call);
        return call;
    }

    public Call<GifInfoObj> getImgList(String appid, String sign, String page, String maxResult) {
        Call<GifInfoObj> call = mJokeApi.getImgList(appid, sign, page, maxResult);
        mCallList.add(call);
        return call;
    }

    public Call<GifInfoObj> getGifList(String appid, String sign, String page, String maxResult) {
        Call<GifInfoObj> call = mJokeApi.getGifList(appid, sign, page, maxResult);
        mCallList.add(call);
        return call;
    }

    //额外提供一个方法用于取消所有Call
    public void cancelAll(Call... excludes) {
        if (excludes.length > 0) {
            mCallList.removeAll(Arrays.asList(excludes));
        }
        if (mCallList != null) {
            for (Call call : mCallList) {
                if (call != null && !call.isCanceled()) {
                    call.cancel();
                }
            }
            mCallList.clear();
            mCallList = null;
        }
    }
}
