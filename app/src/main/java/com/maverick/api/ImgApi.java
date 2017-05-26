package com.maverick.api;

import com.maverick.bean.GifInfoObj;
import com.maverick.global.UrlData;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ll on 2017/5/22.
 */
public interface ImgApi {

    @FormUrlEncoded
    @POST("341-2")
    Observable<GifInfoObj> getImgList(@Field("showapi_appid") String appid,
                                    @Field("showapi_sign") String sign,
                                    @Field("page") String page,
                                    @Field("maxResult") String maxResult);

    @FormUrlEncoded
    @POST("341-1")
    Observable<GifInfoObj> getTextList(@Field("showapi_appid") String appid,
                                      @Field("showapi_sign") String sign,
                                      @Field("page") String page,
                                      @Field("maxResult") String maxResult);

}
