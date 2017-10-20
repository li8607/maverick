package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/20.
 */
public class SinaTabInfo implements Serializable, Cloneable {

    private String title = "";

    private String type = "";

    private String space = "";

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

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    @Override
    public SinaTabInfo clone() {
        SinaTabInfo info = new SinaTabInfo();
        try {
            info = (SinaTabInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return info;
    }
}
