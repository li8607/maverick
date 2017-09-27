package com.maverick.api;

import com.maverick.bean.BeautyInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;

/**
 * Created by limingfei on 2017/9/27.
 */
public class BeautyApiInvokeProxy  {

    private BeautyApi mBeautyApi;

    private List<Call> mCallList = Collections.synchronizedList(new ArrayList<Call>());

    public BeautyApiInvokeProxy(BeautyApi beautyApi) {
        this.mBeautyApi = beautyApi;
    }


    public Call<BeautyInfo> getBeautyInfo(int num, int page) {
        Call<BeautyInfo> call = mBeautyApi.getBeautyInfo(num, page);
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
