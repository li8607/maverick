package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by ll on 2017/5/18.
 */
public class GifInfo implements Serializable {

    public String id;
    public String title;
    public String img;
    public String type;
    public String ct;

    @Override
    public String toString() {
        return "GifInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", type='" + type + '\'' +
                ", ct='" + ct + '\'' +
                '}';
    }
}
