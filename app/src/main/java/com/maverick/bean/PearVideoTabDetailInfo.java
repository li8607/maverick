package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public class PearVideoTabDetailInfo implements Serializable {

    private String resultCode;
    private String resultMsg;
    private String reqId;
    private String systemTime;
    private List<PearVideoInfoHomeArea> areaList;
    private PearVideoTabInfo categoryInfo;
    private String nextUrl;
    private List<PearVideoInfo> hotList;
    private List<PearVideoInfo> contList;

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

    public List<PearVideoInfoHomeArea> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<PearVideoInfoHomeArea> areaList) {
        this.areaList = areaList;
    }

    public PearVideoTabInfo getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(PearVideoTabInfo categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    public List<PearVideoInfo> getHotList() {
        return hotList;
    }

    public void setHotList(List<PearVideoInfo> hotList) {
        this.hotList = hotList;
    }

    public List<PearVideoInfo> getContList() {
        return contList;
    }

    public void setContList(List<PearVideoInfo> contList) {
        this.contList = contList;
    }
}
