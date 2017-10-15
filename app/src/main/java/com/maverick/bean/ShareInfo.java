package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/15.
 */
public class ShareInfo implements Serializable {

    private int shareType;

    private String imageurl;
    private String title;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
