package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2017/10/17.
 */
public class ShareItemInfo implements Serializable {

    private int shareType; //微信、微信朋友圈、新浪微博、QQ空间等类型
    private int id;  //资源图片
    private String title;

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
