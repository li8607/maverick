package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ll on 2017/5/18.
 */
public class GifInfoBody implements Serializable {

    public String allPages;
    public String ret_code;
    public String currentPage;
    public String allNum;
    public String maxResult;
    public List<GifInfo> contentlist;

    @Override
    public String toString() {
        return "GifInfoBody{" +
                "allPages='" + allPages + '\'' +
                ", ret_code='" + ret_code + '\'' +
                ", currentPage='" + currentPage + '\'' +
                ", allNum='" + allNum + '\'' +
                ", maxResult='" + maxResult + '\'' +
                ", contentlist=" + contentlist +
                '}';
    }
}
