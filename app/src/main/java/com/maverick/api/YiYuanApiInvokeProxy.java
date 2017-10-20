package com.maverick.api;

import com.maverick.bean.CaricatureInfoObj;
import com.maverick.bean.GifInfoObj;
import com.maverick.bean.SisterInfoObj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;

/**
 * Created by limingfei on 2017/9/27.
 */
public class YiYuanApiInvokeProxy {

    private YiYuanApi mApi;

    private List<Call> mCallList = Collections.synchronizedList(new ArrayList<Call>());

    public YiYuanApiInvokeProxy(YiYuanApi api) {
        this.mApi = api;
    }

    public Call<SisterInfoObj> getSister(String appid, String sign, String type, String title, String page) {
        Call<SisterInfoObj> call = mApi.getSister(appid, sign, type, title, page);
        mCallList.add(call);
        return call;
    }

    public Call<GifInfoObj> getTextList(String appid, String sign, String page, String maxResult) {
        Call<GifInfoObj> call = mApi.getTextList(appid, sign, page, maxResult);
        mCallList.add(call);
        return call;
    }

    public Call<GifInfoObj> getImgList(String appid, String sign, String page, String maxResult) {
        Call<GifInfoObj> call = mApi.getImgList(appid, sign, page, maxResult);
        mCallList.add(call);
        return call;
    }

    public Call<GifInfoObj> getGifList(String appid, String sign, String page, String maxResult) {
        Call<GifInfoObj> call = mApi.getGifList(appid, sign, page, maxResult);
        mCallList.add(call);
        return call;
    }

    public Call<CaricatureInfoObj> getCaricature(String appid, String sign, String type, String page) {
        Call<CaricatureInfoObj> call = mApi.getCaricature(appid, sign, type, page);
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
