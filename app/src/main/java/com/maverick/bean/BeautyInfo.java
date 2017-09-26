package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by limingfei on 2017/9/26.
 */
public class BeautyInfo implements Serializable {

    private List<BeautyItemInfo> results;

    public List<BeautyItemInfo> getResults() {
        return results;
    }

    public void setResults(List<BeautyItemInfo> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "BeautyInfo{" +
                ", results=" + results +
                '}';
    }
}
