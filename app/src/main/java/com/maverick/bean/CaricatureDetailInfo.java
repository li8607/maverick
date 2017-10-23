package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2017/10/23.
 */
public class CaricatureDetailInfo implements Serializable {

    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
