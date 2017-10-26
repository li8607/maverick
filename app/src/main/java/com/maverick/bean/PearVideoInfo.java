package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2017/10/26.
 */
public class PearVideoInfo implements Serializable{

    private String contId;
    private String name;
    private String pic;
    private PearVideoInfoNode nodeInfo;
    private String link;
    private String linkType;
    private String cornerLabel;
    private String cornerLabelDesc;
    private String forwordType;
    private String videoType;
    private String duration;
    private String liveStatus;
    private String liveStartTime;
    private String praiseTimes;
    private String adExpMonitorUrl;
    private String coverVideo;

    public String getContId() {
        return contId;
    }

    public void setContId(String contId) {
        this.contId = contId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public PearVideoInfoNode getNodeInfo() {
        return nodeInfo;
    }

    public void setNodeInfo(PearVideoInfoNode nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getCornerLabel() {
        return cornerLabel;
    }

    public void setCornerLabel(String cornerLabel) {
        this.cornerLabel = cornerLabel;
    }

    public String getCornerLabelDesc() {
        return cornerLabelDesc;
    }

    public void setCornerLabelDesc(String cornerLabelDesc) {
        this.cornerLabelDesc = cornerLabelDesc;
    }

    public String getForwordType() {
        return forwordType;
    }

    public void setForwordType(String forwordType) {
        this.forwordType = forwordType;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getLiveStartTime() {
        return liveStartTime;
    }

    public void setLiveStartTime(String liveStartTime) {
        this.liveStartTime = liveStartTime;
    }

    public String getPraiseTimes() {
        return praiseTimes;
    }

    public void setPraiseTimes(String praiseTimes) {
        this.praiseTimes = praiseTimes;
    }

    public String getAdExpMonitorUrl() {
        return adExpMonitorUrl;
    }

    public void setAdExpMonitorUrl(String adExpMonitorUrl) {
        this.adExpMonitorUrl = adExpMonitorUrl;
    }

    public String getCoverVideo() {
        return coverVideo;
    }

    public void setCoverVideo(String coverVideo) {
        this.coverVideo = coverVideo;
    }

    @Override
    public String toString() {
        return "PearVideoInfo{" +
                "contId='" + contId + '\'' +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", nodeInfo=" + nodeInfo +
                ", link='" + link + '\'' +
                ", linkType='" + linkType + '\'' +
                ", cornerLabel='" + cornerLabel + '\'' +
                ", cornerLabelDesc='" + cornerLabelDesc + '\'' +
                ", forwordType='" + forwordType + '\'' +
                ", videoType='" + videoType + '\'' +
                ", duration='" + duration + '\'' +
                ", liveStatus='" + liveStatus + '\'' +
                ", liveStartTime='" + liveStartTime + '\'' +
                ", praiseTimes='" + praiseTimes + '\'' +
                ", adExpMonitorUrl='" + adExpMonitorUrl + '\'' +
                ", coverVideo='" + coverVideo + '\'' +
                '}';
    }
}
