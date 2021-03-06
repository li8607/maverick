package com.maverick.bean;

import java.io.Serializable;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by Administrator on 2017/10/15.
 */
public class MenuDetailInfo implements Serializable {

    private int shareType;

    private String imageurl;
    private String title;
    private String videourl;
    private String weburl;
    private String text;
    private Collect collect;

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

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Collect getCollect() {
        return collect;
    }

    public void setCollect(Collect collect) {
        this.collect = collect;
    }
}
