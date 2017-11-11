package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/11.
 */

public class PearUserInfo implements Serializable {

    private String userId;
    private String nickname;
    private String isPaike;
    private String pic;

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
