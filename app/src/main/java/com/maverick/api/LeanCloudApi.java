package com.maverick.api;

import com.maverick.bean.LeanCloudMusicResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by limingfei on 2018/3/7.
 */

public interface LeanCloudApi {
    @Headers({
            "X-LC-Id: 0C89a76qNodEmSHBpCSTGgCX-gzGzoHsz",
            "Content-Type: application/json",
            "X-LC-Key: 2Bcw0fzmDjYF6WXqEJumCKxR"
            })
    @GET("classes/music")
    Call<LeanCloudMusicResults> getMusicList(@Query("limit") int limit
            , @Query("skip") int skip);

}
