package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2017/11/9.
 */
public class PearVideoDetailInfoPostChildItem implements Serializable {

    private String commentId;
    private String content;
    private String pubTime;
    private String topTimes;
    private String stepTimes;
    private String replyTimes;
    private PearUserInfo userInfo;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getTopTimes() {
        return topTimes;
    }

    public void setTopTimes(String topTimes) {
        this.topTimes = topTimes;
    }

    public String getStepTimes() {
        return stepTimes;
    }

    public void setStepTimes(String stepTimes) {
        this.stepTimes = stepTimes;
    }

    public String getReplyTimes() {
        return replyTimes;
    }

    public void setReplyTimes(String replyTimes) {
        this.replyTimes = replyTimes;
    }

    public PearUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(PearUserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
