package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2018/3/7.
 */

public class LeanCloudMusicMid implements Serializable {

    private String name;
    private String objectId;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "LeanCloudMusicMid{" +
                "name='" + name + '\'' +
                ", objectId='" + objectId + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
