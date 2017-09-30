package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2017/9/27.
 */
public class MyInfo implements Serializable{

    private String title;
    private int icon;
    private String type;  //0 浏览记录，1 收藏， 2 设置

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
