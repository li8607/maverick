package com.maverick.imodel;

import com.maverick.bean.PearVideoTabInfo;

import java.util.List;

/**
 * Created by limingfei on 2017/10/20.
 */
public interface IPearModel {

    void release();

    void requestData(OnResultListener listener);

    interface OnResultListener {
        void onSuccess(List<PearVideoTabInfo> list);

        void onFail();
    }
}
