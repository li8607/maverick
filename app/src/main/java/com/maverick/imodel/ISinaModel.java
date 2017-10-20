package com.maverick.imodel;

import com.maverick.bean.CaricatureInfo;
import com.maverick.bean.SinaInfo;

import java.util.List;

/**
 * Created by limingfei on 2017/10/20.
 */
public interface ISinaModel {

    void release();

    void requestData(String type, String space, int page, OnResultListener listener);

    interface OnResultListener {
        void onSuccess(List<SinaInfo> list, boolean isHasMore);

        void onFail();
    }
}
