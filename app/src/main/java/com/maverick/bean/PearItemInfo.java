package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class PearItemInfo implements Serializable {

    private int type;
    private String tabTitle;
    private PearVideoDetailInfo pearVideoDetailInfo;
    private PearVideoInfo pearVideoInfo;
    private List<PearVideoDetailInfoTag> pearTagInfo;
    private PearVideoDetailInfoPostChildItem commentInfo;
    private PearVideoInfoNode pearVideoInfoNode;
    private int lineType;
    private int relateContPosition;
    private int relateContCount;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public PearVideoDetailInfo getPearVideoDetailInfo() {
        return pearVideoDetailInfo;
    }

    public void setPearVideoDetailInfo(PearVideoDetailInfo pearVideoDetailInfo) {
        this.pearVideoDetailInfo = pearVideoDetailInfo;
    }

    public PearVideoInfo getPearVideoInfo() {
        return pearVideoInfo;
    }

    public void setPearVideoInfo(PearVideoInfo pearVideoInfo) {
        this.pearVideoInfo = pearVideoInfo;
    }

    public List<PearVideoDetailInfoTag> getPearTagInfo() {
        return pearTagInfo;
    }

    public void setPearTagInfo(List<PearVideoDetailInfoTag> pearTagInfo) {
        this.pearTagInfo = pearTagInfo;
    }

    public PearVideoDetailInfoPostChildItem getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(PearVideoDetailInfoPostChildItem commentInfo) {
        this.commentInfo = commentInfo;
    }

    public int getLineType() {
        return lineType;
    }

    public void setLineType(int lineType) {
        this.lineType = lineType;
    }

    public PearVideoInfoNode getPearVideoInfoNode() {
        return pearVideoInfoNode;
    }

    public void setPearVideoInfoNode(PearVideoInfoNode pearVideoInfoNode) {
        this.pearVideoInfoNode = pearVideoInfoNode;
    }

    public void setRelateContPosition(int position) {
        this.relateContPosition = position;
    }

    public int getRelateContPosition() {
        return relateContPosition;
    }

    public int getRelateContCount() {
        return relateContCount;
    }

    public void setRelateContCount(int relateContCount) {
        this.relateContCount = relateContCount;
    }
}
