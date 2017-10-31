package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */

public class PearVideoDetailInfo implements Serializable {

    private String contId;
    private String name;
    private String summary;
    private String source;
    private String pubTime;
    private String isVr;
    private String aspectRatio;
    private String contentHtml;
    private String liveHtml;
    private String postHtml;
    private String praiseTimes;
    private String commentTimes;
    private String isFavorited;
    private String pic;
    private String postId;
    private String liveRoomId;
    private String sharePic;
    private String shareUrl;
    private PearVideoDetailInfoLive liveInfo;
    private String cornerLabel;
    private String cornerLabelDesc;
    private List<PearVideoInfoAuthor> authors;
    private List<PearVideoDetailInfoTag> tags;
    private List<PearVideoDetailInfoVideo> videos;
    private PearVideoInfoNode nodeInfo;
    private String copyright;
    private String isDownload;
    private String duration;
    private String adMonitorUrl;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getIsVr() {
        return isVr;
    }

    public void setIsVr(String isVr) {
        this.isVr = isVr;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getLiveHtml() {
        return liveHtml;
    }

    public void setLiveHtml(String liveHtml) {
        this.liveHtml = liveHtml;
    }

    public String getPostHtml() {
        return postHtml;
    }

    public void setPostHtml(String postHtml) {
        this.postHtml = postHtml;
    }

    public String getPraiseTimes() {
        return praiseTimes;
    }

    public void setPraiseTimes(String praiseTimes) {
        this.praiseTimes = praiseTimes;
    }

    public String getCommentTimes() {
        return commentTimes;
    }

    public void setCommentTimes(String commentTimes) {
        this.commentTimes = commentTimes;
    }

    public String getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(String isFavorited) {
        this.isFavorited = isFavorited;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getLiveRoomId() {
        return liveRoomId;
    }

    public void setLiveRoomId(String liveRoomId) {
        this.liveRoomId = liveRoomId;
    }

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public PearVideoDetailInfoLive getLiveInfo() {
        return liveInfo;
    }

    public void setLiveInfo(PearVideoDetailInfoLive liveInfo) {
        this.liveInfo = liveInfo;
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

    public List<PearVideoInfoAuthor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<PearVideoInfoAuthor> authors) {
        this.authors = authors;
    }

    public List<PearVideoDetailInfoTag> getTags() {
        return tags;
    }

    public void setTags(List<PearVideoDetailInfoTag> tags) {
        this.tags = tags;
    }

    public List<PearVideoDetailInfoVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<PearVideoDetailInfoVideo> videos) {
        this.videos = videos;
    }

    public PearVideoInfoNode getNodeInfo() {
        return nodeInfo;
    }

    public void setNodeInfo(PearVideoInfoNode nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getIsDownload() {
        return isDownload;
    }

    public void setIsDownload(String isDownload) {
        this.isDownload = isDownload;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAdMonitorUrl() {
        return adMonitorUrl;
    }

    public void setAdMonitorUrl(String adMonitorUrl) {
        this.adMonitorUrl = adMonitorUrl;
    }
}
