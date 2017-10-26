package com.maverick.api;

import com.maverick.bean.Info;
import com.maverick.bean.PearVideoInfoHome;
import com.maverick.bean.PearVideoInfoObj;
import com.maverick.bean.PearVideoTabDetailInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by limingfei on 2017/9/27.
 */
public interface PearApi {

    @GET("getCategorys.jsp")
    Call<PearVideoInfoObj> getPearTabList();

    @FormUrlEncoded
    @POST("getCategoryConts.jsp")
    Call<PearVideoTabDetailInfo> getPearTabDetail(@Field("hotPageidx") String hotPageidx
            , @Field("categoryId") String categoryId);

    @GET("home.jsp")
    Call<PearVideoInfoHome> getPearVideoHome(@Query("lastLikeIds") String lastLikeIds);

    @FormUrlEncoded
    @POST("login")
    Call<Info> login(@Field("username") String username
            , @Field("password") String password);

}
