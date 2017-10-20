package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 */
public class CaricatureInfoPageBean implements Serializable {

    private boolean hasMorePage;
    private List<CaricatureInfo> contentlist;
    private String currentPage;
    private String maxResult;

    public boolean isHasMorePage() {
        return hasMorePage;
    }

    public void setHasMorePage(boolean hasMorePage) {
        this.hasMorePage = hasMorePage;
    }

    public List<CaricatureInfo> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<CaricatureInfo> contentlist) {
        this.contentlist = contentlist;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(String maxResult) {
        this.maxResult = maxResult;
    }
}
