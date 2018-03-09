package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2018/3/9.
 */

public class SettingItemInfo implements Serializable {

    private String title;
    private String type;
    private int state; // 1, 显示切换按钮， 2，显示文字
    private String groupType;

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    @Override
    public String toString() {
        return "SettingItemInfo{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
