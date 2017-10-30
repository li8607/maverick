package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/30.
 */

public class PearVideoInfoAuthor implements Serializable {

    private String userId;
    private String nickname;
    private String isPaike;
    private String paikeType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIsPaike() {
        return isPaike;
    }

    public void setIsPaike(String isPaike) {
        this.isPaike = isPaike;
    }

    public String getPaikeType() {
        return paikeType;
    }

    public void setPaikeType(String paikeType) {
        this.paikeType = paikeType;
    }
}
