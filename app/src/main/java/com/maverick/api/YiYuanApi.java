package com.maverick.api;

import com.maverick.bean.CaricatureInfoObj;
import com.maverick.bean.GifInfoObj;
import com.maverick.bean.SisterInfoObj;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/9/29.
 */
public interface YiYuanApi {

    @FormUrlEncoded
    @POST("255-1")
    Call<SisterInfoObj> getSister(@Field("showapi_appid") String appid
            , @Field("showapi_sign") String sign
            , @Field("type") String type
            , @Field("title") String title
            , @Field("page") String page);

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

    @FormUrlEncoded
    @POST("958-1")
    Call<CaricatureInfoObj> getCaricature(@Field("showapi_appid") String appid
            , @Field("showapi_sign") String sign
            , @Field("type") String type
            , @Field("page") String page);
}
