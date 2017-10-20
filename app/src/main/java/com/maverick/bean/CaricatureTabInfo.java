package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/30.
 */
public class CaricatureTabInfo implements Serializable {

    private String type = "";

    private String title = "";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
