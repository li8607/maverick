package com.maverick.imodel;

import com.maverick.bean.SisterInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/30.
 */
public interface ISisterModel {

    void release();

    void requestData(String type, String title, int page, OnResultListener listener);

    interface OnResultListener {
        void onSuccess(List<SisterInfo> list);

        void onFail();
    }

}
