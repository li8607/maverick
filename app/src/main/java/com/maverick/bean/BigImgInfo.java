package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/9/27.
 */
public class BigImgInfo implements Serializable {

    private String img;
    private List<String> imgs;
    private String title;
    private String webUrl;
    private String sharedElementName;

    private Collect collect;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Collect getCollect() {
        return collect;
    }

    public void setCollect(Collect collect) {
        this.collect = collect;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getSharedElementName() {
        return sharedElementName;
    }

    public void setSharedElementName(String sharedElementName) {
        this.sharedElementName = sharedElementName;
    }
}
