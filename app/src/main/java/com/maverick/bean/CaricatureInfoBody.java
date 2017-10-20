package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ll on 2017/5/18.
 */
public class CaricatureInfoBody implements Serializable {

    public String ret_code;
    public CaricatureInfoPageBean pagebean;

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public CaricatureInfoPageBean getPagebean() {
        return pagebean;
    }

    public void setPagebean(CaricatureInfoPageBean pagebean) {
        this.pagebean = pagebean;
    }
}
