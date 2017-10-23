package com.maverick.api;

import com.maverick.bean.CaricatureInfoObj;
import com.maverick.bean.CaricatureListInfoObj;
import com.maverick.bean.GifInfoObj;
import com.maverick.bean.SinaInfoObj;
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

    @FormUrlEncoded
    @POST("958-2")
    Call<CaricatureListInfoObj> getCaricatureDetail(@Field("showapi_appid") String appid
            , @Field("showapi_sign") String sign
            , @Field("id") String id);

    @FormUrlEncoded
    @POST("254-1")
    Call<SinaInfoObj> getSina(@Field("showapi_appid") String appid
            , @Field("showapi_sign") String sign
            , @Field("typeId") String typeId   //类型id
            , @Field("space") String space    //类容，分为日榜=day,周榜=week,月榜=month
            , @Field("key") String key      // 微博主人名称关键词
            , @Field("date") String date   //榜单日期。格式yyyyMMdd,如果是周榜，则此日期为上一周的星期1,如果是月榜，则此日期为上一月的第1天
            , @Field("page") String page);
}
