package com.maverick.imodel;

import com.maverick.bean.GifInfoObj;

/**
 * Created by limingfei on 2017/9/27.
 */
public interface IJokeModel {

    void release();

    void requestTextData(int page, int num, OnResultListener listener);

    interface OnResultListener {
        void onSuccess(GifInfoObj infoObj);
        void onFail();
    }
}
