package com.maverick.api;

import com.maverick.bean.SisterInfoObj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;

/**
 * Created by limingfei on 2017/9/27.
 */
public class SisterApiInvokeProxy {

    private SisterApi mSisterApi;

    private List<Call> mCallList = Collections.synchronizedList(new ArrayList<Call>());

    public SisterApiInvokeProxy(SisterApi sisterApi) {
        this.mSisterApi = sisterApi;
    }

    public Call<SisterInfoObj> getSisterList(String appid, String sign, String type, String title, String page) {
        Call<SisterInfoObj> call = mSisterApi.getSisterList(appid, sign, type, title, page);
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
