package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2017/9/25.
 */
public class ButtonInfo implements Serializable {

    private String name;
    private int iconId;
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
