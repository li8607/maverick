package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/29.
 */
public class SisterInfoBody implements Serializable{

    public SisterInfoPageBean pagebean;
    public String ret_code;

    public SisterInfoPageBean getPagebean() {
        return pagebean;
    }

    public void setPagebean(SisterInfoPageBean pagebean) {
        this.pagebean = pagebean;
    }

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    @Override
    public String toString() {
        return "SisterInfoBody{" +
                "pagebean=" + pagebean +
                ", ret_code='" + ret_code + '\'' +
                '}';
    }
}
