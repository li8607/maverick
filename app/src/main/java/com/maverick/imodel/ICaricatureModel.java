package com.maverick.imodel;

import com.maverick.bean.CaricatureInfo;

import java.util.List;

/**
 * Created by limingfei on 2017/10/20.
 */
public interface ICaricatureModel {

    void release();

    void requestData(String type, int page, OnResultListener listener);

    interface OnResultListener {
        void onSuccess(List<CaricatureInfo> list, boolean isHasMore);

        void onFail();
    }
}
