package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2017/9/27.
 */
public class MyInfo implements Serializable{

    private String title;
    private String nickname;
    private String headUrl;
    private String username;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
