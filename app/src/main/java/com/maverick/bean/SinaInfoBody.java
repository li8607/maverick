package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by ll on 2017/5/18.
 */
public class SinaInfoBody implements Serializable {

    public String ret_code;
    public SinaInfoPageBean pagebean;

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public SinaInfoPageBean getPagebean() {
        return pagebean;
    }

    public void setPagebean(SinaInfoPageBean pagebean) {
        this.pagebean = pagebean;
    }
}
