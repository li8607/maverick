package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by ll on 2017/5/18.
 */
public class GifInfoObj implements Serializable {

    public String showapi_res_code;
    public String showapi_res_error;
    public GifInfoBody showapi_res_body;


    @Override
    public String toString() {
        return "GifInfoObj{" +
                "showapi_res_code='" + showapi_res_code + '\'' +
                ", showapi_res_error='" + showapi_res_error + '\'' +
                ", showapi_res_body=" + showapi_res_body +
                '}';
    }
}
