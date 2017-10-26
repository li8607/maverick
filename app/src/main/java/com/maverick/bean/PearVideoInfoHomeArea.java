package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2017/10/26.
 */
public class PearVideoInfoHomeArea implements Serializable {

    private String area_id;
    private PearVideoInfoHomeAreaExpInfo expInfo;

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public PearVideoInfoHomeAreaExpInfo getExpInfo() {
        return expInfo;
    }

    public void setExpInfo(PearVideoInfoHomeAreaExpInfo expInfo) {
        this.expInfo = expInfo;
    }
}
