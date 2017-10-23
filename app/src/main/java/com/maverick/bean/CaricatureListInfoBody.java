package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by ll on 2017/5/18.
 */
public class CaricatureListInfoBody implements Serializable {

    public String ret_code;
    public CaricatureListInfo item;

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public CaricatureListInfo getItem() {
        return item;
    }

    public void setItem(CaricatureListInfo item) {
        this.item = item;
    }
}
