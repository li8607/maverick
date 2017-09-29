package com.maverick.api;

import com.maverick.bean.SisterInfoObj;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/9/29.
 */
public interface SisterApi {

    @FormUrlEncoded
    @POST("255-1")
    Call<SisterInfoObj> getSisterList(@Field("showapi_appid") String appid
            , @Field("showapi_sign") String sign
            , @Field("type") String type
            , @Field("title") String title
            , @Field("page") String page);
}
