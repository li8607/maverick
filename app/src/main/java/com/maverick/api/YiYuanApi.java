package com.maverick.api;

import com.maverick.bean.CaricatureInfoObj;
import com.maverick.bean.CaricatureListInfoObj;
import com.maverick.bean.GifInfoObj;
import com.maverick.bean.SinaInfoObj;
import com.maverick.bean.SisterInfoObj;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/9/29.
 */
public interface YiYuanApi {

    @GET("255-1")
    Call<SisterInfoObj> getSister(@Query("showapi_appid") String appid
            , @Query("showapi_sign") String sign
            , @Query("type") String type
            , @Query("title") String title
            , @Query("page") String page);

    @GET("341-1")
    Call<GifInfoObj> getTextList(@Query("showapi_appid") String appid,
                                 @Query("showapi_sign") String sign,
                                 @Query("page") String page,
                                 @Query("maxResult") String maxResult);

    @GET("341-2")
    Call<GifInfoObj> getImgList(@Query("showapi_appid") String appid,
                                @Query("showapi_sign") String sign,
                                @Query("page") String page,
                                @Query("maxResult") String maxResult);

    @GET("341-3")
    Call<GifInfoObj> getGifList(@Query("showapi_appid") String appid,
                                @Query("showapi_sign") String sign,
                                @Query("page") String page,
                                @Query("maxResult") String maxResult);

    @GET("958-1")
    Call<CaricatureInfoObj> getCaricature(@Query("showapi_appid") String appid
            , @Query("showapi_sign") String sign
            , @Query("type") String type
            , @Query("page") String page);

    @GET("958-2")
    Call<CaricatureListInfoObj> getCaricatureDetail(@Query("showapi_appid") String appid
            , @Query("showapi_sign") String sign
            , @Query("id") String id);

    @GET("254-1")
    Call<SinaInfoObj> getSina(@Query("showapi_appid") String appid
            , @Query("showapi_sign") String sign
            , @Query("typeId") String typeId   //类型id
            , @Query("space") String space    //类容，分为日榜=day,周榜=week,月榜=month
            , @Query("key") String key      // 微博主人名称关键词
            , @Query("date") String date   //榜单日期。格式yyyyMMdd,如果是周榜，则此日期为上一周的星期1,如果是月榜，则此日期为上一月的第1天
            , @Query("page") String page);
}
