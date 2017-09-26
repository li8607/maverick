package com.maverick.model;

import com.android.wonderokhttp.http.HttpUtil;
import com.android.wonderokhttp.http.listener.JsonHttpListener;
import com.maverick.bean.BeautyInfo;
import com.maverick.imodel.IBeautyModel;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by limingfei on 2017/9/26.
 */
public class BeautyModel implements IBeautyModel {

    private String url = "http://gank.io/api/data/福利/";

    @Override
    public void requestData(int page, int num, final OnResultListener listener) {

        if(listener == null) {
            return;
        }

        String url = this.url + page + "/" + num;
        HttpUtil.exec(url, new JsonHttpListener<BeautyInfo>() {
            @Override
            protected void onFailure(Throwable throwable, BeautyInfo beautyInfo) {
                listener.onFail();
            }

            @Override
            protected void onSuccess(int i, Map<String, String> map, BeautyInfo beautyInfo, JSONObject jsonObject, boolean b) {
                if(!beautyInfo.isError()) {
                    listener.onSuccess(beautyInfo.getResults());
                }
            }
        });
    }
}
