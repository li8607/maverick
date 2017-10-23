package com.maverick.bean;

import java.io.Serializable;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/10/20.
 */
public class WebDetailInfo implements Serializable {

    private String webUrl;
    private String title;
    private String imageUrl;
    private Collect collect;

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Collect getCollect() {
        return collect;
    }

    public void setCollect(Collect collect) {
        this.collect = collect;
    }
}
