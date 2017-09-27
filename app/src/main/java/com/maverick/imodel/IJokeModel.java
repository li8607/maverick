package com.maverick.imodel;

import com.maverick.bean.GifInfoObj;

/**
 * Created by limingfei on 2017/9/27.
 */
public interface IJokeModel {

    void release();

    void requestTextData(int page, int num, OnTextResultListener listener);

    void requestImgData(int page, int num, OnImgResultListener listener);

    interface OnTextResultListener {
        void onSuccess(GifInfoObj infoObj);
        void onFail();
    }

    interface OnImgResultListener {
        void onSuccess(GifInfoObj infoObj);
        void onFail();
    }
}
