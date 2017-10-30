package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/30.
 */

public class PearVideoDetailInfoTag implements Serializable {

    private String tagId;
    private String name;

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
