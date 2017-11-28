package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2017/9/27.
 */
public class MyInfo implements Serializable{

    private String title;
    private int icon;
    private int type;  //0 浏览记录，1 收藏， 2 设置

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
