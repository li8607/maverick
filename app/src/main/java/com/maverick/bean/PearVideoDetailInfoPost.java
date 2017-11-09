package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */

public class PearVideoDetailInfoPost implements Serializable {

    private String postId;
    private String name;
    private String commentTimes;
    private PearVideoDetailInfoPostCommunity communityInfo;
    private String isfavorited;
    private String postHtml;
    private List<PearVideoDetailInfoPostChildItem> childList;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommentTimes() {
        return commentTimes;
    }

    public void setCommentTimes(String commentTimes) {
        this.commentTimes = commentTimes;
    }

    public PearVideoDetailInfoPostCommunity getCommunityInfo() {
        return communityInfo;
    }

    public void setCommunityInfo(PearVideoDetailInfoPostCommunity communityInfo) {
        this.communityInfo = communityInfo;
    }

    public String getIsfavorited() {
        return isfavorited;
    }

    public void setIsfavorited(String isfavorited) {
        this.isfavorited = isfavorited;
    }

    public String getPostHtml() {
        return postHtml;
    }

    public void setPostHtml(String postHtml) {
        this.postHtml = postHtml;
    }

    public List<PearVideoDetailInfoPostChildItem> getChildList() {
        return childList;
    }

    public void setChildList(List<PearVideoDetailInfoPostChildItem> childList) {
        this.childList = childList;
    }
}
