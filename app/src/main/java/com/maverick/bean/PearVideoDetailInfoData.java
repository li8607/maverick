package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */

public class PearVideoDetailInfoData implements Serializable {

    private String resultCode;
    private String resultMsg;
    private String reqId;
    private String systemTime;
    private List<PearVideoInfoHomeArea> areaList;
    private PearVideoDetailInfo content;
    private PearVideoDetailInfoNext nextInfo;
    private PearVideoDetailInfoPost postInfo;
    private List<PearVideoInfo> relateConts;

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

    public PearVideoDetailInfo getContent() {
        return content;
    }

    public void setContent(PearVideoDetailInfo content) {
        this.content = content;
    }

    public PearVideoDetailInfoNext getNextInfo() {
        return nextInfo;
    }

    public void setNextInfo(PearVideoDetailInfoNext nextInfo) {
        this.nextInfo = nextInfo;
    }

    public PearVideoDetailInfoPost getPostInfo() {
        return postInfo;
    }

    public void setPostInfo(PearVideoDetailInfoPost postInfo) {
        this.postInfo = postInfo;
    }

    public List<PearVideoInfo> getRelateConts() {
        return relateConts;
    }

    public void setRelateConts(List<PearVideoInfo> relateConts) {
        this.relateConts = relateConts;
    }
}
