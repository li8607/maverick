package com.maverick.api;

import com.maverick.bean.BeautyInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/9/27.
 */
public interface BeautyApi {

    @GET("data/福利/{num}/{page}")
    Call<BeautyInfo> getBeautyInfo(@Path("num") int num, @Path("page") int page);

//    @GET("day/{year}/{month}/{day}") Observable<GankData> getGankData(
//            @Path("year") int year,
//            @Path("month") int month,
//            @Path("day") int day);
//
//    @GET("data/休息视频/" + DrakeetFactory.meizhiSize + "/{page}")
//    Observable<休息视频Data> get休息视频Data(@Path("page") int page);

}
