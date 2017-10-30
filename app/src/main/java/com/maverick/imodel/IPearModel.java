package com.maverick.imodel;

import com.maverick.bean.PearVideoTabDetailInfo;
import com.maverick.bean.PearVideoTabInfo;

import java.util.List;

/**
 * Created by limingfei on 2017/10/20.
 */
public interface IPearModel {

    void release();

    void requestData(OnResultListener listener);

    void requestTabItemData(int hotPageidx, String categoryId, OnTabItemResultListener listener);

    void requestTabItemNextData(String url, OnTabItemResultListener listener);

    interface OnResultListener {
        void onSuccess(List<PearVideoTabInfo> list);

        void onFail();
    }

    interface OnTabItemResultListener {
        void onSuccess(PearVideoTabDetailInfo info);

        void onFail();
    }
}
