package com.maverick.api;

import com.maverick.bean.PearVideoDetailInfoData;
import com.maverick.bean.PearVideoInfoObj;
import com.maverick.bean.PearVideoTabDetailInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;

/**
 * Created by limingfei on 2017/9/27.
 */
public class PearApiInvokeProxy {

    private PearApi mApi;

    private List<Call> mCallList = Collections.synchronizedList(new ArrayList<Call>());

    public PearApiInvokeProxy(PearApi api) {
        this.mApi = api;
    }


    public Call<PearVideoInfoObj> getPearTabList() {
        Call<PearVideoInfoObj> call = mApi.getPearTabList();
        mCallList.add(call);
        return call;
    }

    public Call<PearVideoTabDetailInfo> getPearTabDetail(String hotPageidx
            , String categoryId) {
        Call<PearVideoTabDetailInfo> call = mApi.getPearTabDetail(hotPageidx, categoryId);
        mCallList.add(call);
        return call;
    }

    public Call<PearVideoTabDetailInfo> getPearTabNextDetail(String url) {
        Call<PearVideoTabDetailInfo> call = mApi.getPearTabNextDetail(url);
        mCallList.add(call);
        return call;
    }

    public Call<PearVideoDetailInfoData> getPearDetail(String contId) {
        Call<PearVideoDetailInfoData> call = mApi.getPearDetail(contId);
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
