package cntv.greendaolibrary.dbbean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

/**
 * Created by limingfei on 2017/9/27.
 */
@Entity
public class Collect implements Serializable {
    @Id
    private Long id;

    @Property(nameInDb = "COLLECTIMAGE")
    private String collectImage;

    @Property(nameInDb = "COLLECTNAME")
    private String collectName;

    @Property(nameInDb = "COLLECTCT")
    private String collectCT;           //网络数据显示的时间

    @Property(nameInDb = "COLLECTURL")
    private String collectUrl;

    @Property(nameInDb = "COLLECTUSERID")
    private String collectUserId;

    @Property(nameInDb = "COLLECTTIME") //收藏时间
    private long collectTime;

    @Property(nameInDb = "COLLECTID")
    private String collectId;

    @Property(nameInDb = "COLLECTITEMTYPE")
    private String collectItemType;  // 1.文本；2.图片；3.动图;  大分类下的小分类

    @Property(nameInDb = "COLLECTTYPE")
    private String collectType;  // 1.笑话；2.美女

    @Property(nameInDb = "USERID")
    private String userId;  // 1.用户Id

    private boolean check;

    public String getCollectType() {
        return this.collectType;
    }

    public void setCollectType(String collectType) {
        this.collectType = collectType;
    }

    public String getCollectItemType() {
        return this.collectItemType;
    }

    public void setCollectItemType(String collectItemType) {
        this.collectItemType = collectItemType;
    }

    public long getCollectTime() {
        return this.collectTime;
    }

    public void setCollectTime(long collectTime) {
        this.collectTime = collectTime;
    }

    public String getCollectName() {
        return this.collectName;
    }

    public void setCollectName(String collectName) {
        this.collectName = collectName;
    }

    public String getCollectImage() {
        return this.collectImage;
    }

    public void setCollectImage(String collectImage) {
        this.collectImage = collectImage;
    }

    public String getCollectCT() {
        return this.collectCT;
    }

    public void setCollectCT(String collectCT) {
        this.collectCT = collectCT;
    }

    public boolean getCheck() {
        return this.check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getCollectUserId() {
        return this.collectUserId;
    }

    public void setCollectUserId(String collectUserId) {
        this.collectUserId = collectUserId;
    }

    public String getCollectUrl() {
        return this.collectUrl;
    }

    public void setCollectUrl(String collectUrl) {
        this.collectUrl = collectUrl;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCollectId() {
        return this.collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Generated(hash = 1840685684)
    public Collect(Long id, String collectImage, String collectName,
            String collectCT, String collectUrl, String collectUserId,
            long collectTime, String collectId, String collectItemType,
            String collectType, String userId, boolean check) {
        this.id = id;
        this.collectImage = collectImage;
        this.collectName = collectName;
        this.collectCT = collectCT;
        this.collectUrl = collectUrl;
        this.collectUserId = collectUserId;
        this.collectTime = collectTime;
        this.collectId = collectId;
        this.collectItemType = collectItemType;
        this.collectType = collectType;
        this.userId = userId;
        this.check = check;
    }

    @Generated(hash = 1726975718)
    public Collect() {
    }

}
