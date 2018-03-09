package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by limingfei on 2018/3/9.
 */

public class SettingTabInfo implements Serializable {

    private String title;
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SettingTabInfo{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
