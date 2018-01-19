package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2018/1/19.
 */

public class ThemeInfo implements Serializable {

    private int colorId;
    private String title;
    private int themeType;

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getThemeType() {
        return themeType;
    }

    public void setThemeType(int themeType) {
        this.themeType = themeType;
    }
}
