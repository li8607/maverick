package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2017/10/26.
 */
public class PearVideoTabInfo implements Serializable {

    private String categoryId;
    private String name;
    private String color;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "PearVideoTabInfo{" +
                "categoryId='" + categoryId + '\'' +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
