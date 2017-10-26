package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2017/10/26.
 */
public class Info implements Serializable {

    private String code;
    private String sessionToken;
    private String updatedAt;
    private String phone;
    private String objectId;
    private String username;
    private String createdAt;
    private String emailVerified;
    private String mobilePhoneVerified;

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getMobilePhoneVerified() {
        return mobilePhoneVerified;
    }

    public void setMobilePhoneVerified(String mobilePhoneVerified) {
        this.mobilePhoneVerified = mobilePhoneVerified;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Info{" +
                "code='" + code + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", phone='" + phone + '\'' +
                ", objectId='" + objectId + '\'' +
                ", username='" + username + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", emailVerified='" + emailVerified + '\'' +
                ", mobilePhoneVerified='" + mobilePhoneVerified + '\'' +
                '}';
    }
}
