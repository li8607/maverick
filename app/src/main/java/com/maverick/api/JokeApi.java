package com.maverick.api;

import com.maverick.bean.GifInfoObj;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by limingfei on 2017/9/27.
 */
public interface JokeApi {

    @FormUrlEncoded
    @POST("341-1")
    Call<GifInfoObj> getTextList(@Field("showapi_appid") String appid,
                                       @Field("showapi_sign") String sign,
                                       @Field("page") String page,
                                       @Field("maxResult") String maxResult);

    @FormUrlEncoded
    @POST("341-2")
    Call<GifInfoObj> getImgList(@Field("showapi_appid") String appid,
                                @Field("showapi_sign") String sign,
                                @Field("page") String page,
                                @Field("maxResult") String maxResult);

    @FormUrlEncoded
    @POST("341-3")
    Call<GifInfoObj> getGifList(@Field("showapi_appid") String appid,
                                @Field("showapi_sign") String sign,
                                @Field("page") String page,
                                @Field("maxResult") String maxResult);
}
