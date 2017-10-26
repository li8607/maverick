package com.maverick.hepler;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/10/26.
 */

public class ClientHelper {

    public static OkHttpClient genericClientPear() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("X-Channel-Code", "official")
                                .addHeader("X-Client-Agent", "Xiaomi")
                                .addHeader("X-Client-Hash", "2f3d6ffkda95dlz2fhju8d3s6dfges3t")
                                .addHeader("X-Client-ID", "123456789123456")
                                .addHeader("X-Client-Version", "2.3.2")
                                .addHeader("X-Long-Token", "")
                                .addHeader("X-Platform-Type", "0")
                                .addHeader("X-Platform-Version", "5.0")
                                .addHeader("X-Serial-Num", "1492140134")
                                .addHeader("X-User-ID", "")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();
        return httpClient;
    }
}
