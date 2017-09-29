package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 */
public class SisterInfoPageBean implements Serializable {

    private String allNum;
    private String allPages;
    private List<SisterInfo> contentlist;
    private String currentPage;
    private String maxResult;

    public String getAllNum() {
        return allNum;
    }

    public void setAllNum(String allNum) {
        this.allNum = allNum;
    }

    public String getAllPages() {
        return allPages;
    }

    public void setAllPages(String allPages) {
        this.allPages = allPages;
    }

    public List<SisterInfo> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<SisterInfo> contentlist) {
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

    @Override
    public String toString() {
        return "SisterInfoPageBean{" +
                "allNum='" + allNum + '\'' +
                ", allPages='" + allPages + '\'' +
                ", contentlist=" + contentlist +
                ", currentPage='" + currentPage + '\'' +
                ", maxResult='" + maxResult + '\'' +
                '}';
    }
}
