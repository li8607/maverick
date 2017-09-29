package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/29.
 */
public class SisterInfoObj implements Serializable{

    public String showapi_res_code;
    public String showapi_res_error;
    public SisterInfoBody showapi_res_body;

    public String getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(String showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public SisterInfoBody getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(SisterInfoBody showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }
}
