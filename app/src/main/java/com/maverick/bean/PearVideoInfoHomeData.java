package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by limingfei on 2017/10/26.
 */
public class PearVideoInfoHomeData implements Serializable {

    private String nodeType;
    private String nodeName;
    private String isOrder;
    private String nodeLogo;
    private String nodeDesc;
    private String moreId;
    private List<PearVideoInfoHomeDataCont> contList;
    private List<PearVideoInfoHomeDataActivity> activityList;

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(String isOrder) {
        this.isOrder = isOrder;
    }

    public String getNodeLogo() {
        return nodeLogo;
    }

    public void setNodeLogo(String nodeLogo) {
        this.nodeLogo = nodeLogo;
    }

    public String getNodeDesc() {
        return nodeDesc;
    }

    public void setNodeDesc(String nodeDesc) {
        this.nodeDesc = nodeDesc;
    }

    public String getMoreId() {
        return moreId;
    }

    public void setMoreId(String moreId) {
        this.moreId = moreId;
    }

    public List<PearVideoInfoHomeDataCont> getContList() {
        return contList;
    }

    public void setContList(List<PearVideoInfoHomeDataCont> contList) {
        this.contList = contList;
    }

    public List<PearVideoInfoHomeDataActivity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<PearVideoInfoHomeDataActivity> activityList) {
        this.activityList = activityList;
    }

    @Override
    public String toString() {
        return "PearVideoInfoHomeData{" +
                "nodeType='" + nodeType + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", isOrder='" + isOrder + '\'' +
                ", nodeLogo='" + nodeLogo + '\'' +
                ", nodeDesc='" + nodeDesc + '\'' +
                ", moreId='" + moreId + '\'' +
                ", contList=" + contList +
                ", activityList=" + activityList +
                '}';
    }
}
