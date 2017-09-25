package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/25.
 */
public class JokeTabInfo implements Serializable{

    private String title;

    private int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
