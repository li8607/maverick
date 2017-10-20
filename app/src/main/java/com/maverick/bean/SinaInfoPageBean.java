package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 */
public class SinaInfoPageBean implements Serializable {

    private int allPages;
    private List<SinaInfo> contentlist;
    private int currentPage;
    private int maxResult;

    public List<SinaInfo> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<SinaInfo> contentlist) {
        this.contentlist = contentlist;
    }

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public boolean isHasMorePage() {
        return currentPage < allPages;
    }
}
