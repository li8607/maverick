package com.maverick.imodel;

import com.maverick.bean.BeautyItemInfo;

import java.util.List;

/**
 * Created by limingfei on 2017/9/26.
 */
public interface IBeautyModel {

    void release();

    void requestData(int page, int num, OnResultListener listener);

    interface OnResultListener {
        void onSuccess(List<BeautyItemInfo> beautyInfo);
        void onFail();
    }
}
