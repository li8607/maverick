package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/31.
 */

public class PearVideoDetailBean implements Serializable {

    private String contId;
    private String name;
    private String pic;

    public String getContId() {
        return contId;
    }

    public void setContId(String contId) {
        this.contId = contId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
