package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by limingfei on 2017/10/26.
 */
public class PearVideoInfoObj implements Serializable {

    private String resultCode;
    private String resultMsg;
    private String reqId;
    private String systemTime;
    private List<PearVideoTabInfo> categoryList;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public List<PearVideoTabInfo> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<PearVideoTabInfo> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString() {
        return "PearVideoInfoObj{" +
                "resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", reqId='" + reqId + '\'' +
                ", systemTime='" + systemTime + '\'' +
                ", categoryList=" + categoryList +
                '}';
    }
}
