package com.maverick.api;

import com.maverick.bean.Info;
import com.maverick.bean.LiVideoInfoObj;
import com.maverick.bean.PearVideoInfoHome;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by limingfei on 2017/9/27.
 */
public interface LiVideoApi {
//    @Headers({
//            "X-Channel-Code:official",
//            "X-Client-Agent:Xiaomi",
//            "X-Client-Hash:2f3d6ffkda95dlz2fhju8d3s6dfges3t",
//            "X-Client-ID:123456789123456",
//            "X-Client-Version:2.3.2",
//            "X-Long-Token:\"\"",
//            "X-Platform-Type:0",
//            "X-Platform-Version:5.0",
//            "X-Serial-Num:1492140134",
//            "X-User-ID:\"\""})

    @GET("getCategorys.jsp")
    Call<LiVideoInfoObj> getLiVideo();


    @GET("home.jsp")
    Call<PearVideoInfoHome> getPearVideoHome(@Query("lastLikeIds") String lastLikeIds);

    @FormUrlEncoded
    @POST("login")
    Call<Info> login(@Field("username") String username
            , @Field("password") String password);

}
