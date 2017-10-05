package cntv.greendaolibrary.dbbean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by limingfei on 2017/9/27.
 */
@Entity
public class Collect {
    @Id
    @Property(nameInDb = "COLLECTMAJORKEY")
    private String collectMajorKey;
    @Property(nameInDb = "COLLECTIMAGE")
    private String collectImage;
    @Property(nameInDb = "COLLECTNAME")
    private String collectName;
    @Property(nameInDb = "COLLECTCT")
    private String collectCT;
    @Property(nameInDb = "COLLECTTIME") //收藏时间
    private long collectTime;
    @Property(nameInDb = "COLLECTITEMTYPE")
    private String collectItemType;  // 1.文本；2.图片；3.动图;
    @Property(nameInDb = "COLLECTTYPE")
    private String collectType;  // 1.笑话；2.美女
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

    public String getCollectMajorKey() {
        return this.collectMajorKey;
    }
    public void setCollectMajorKey(String collectMajorKey) {
        this.collectMajorKey = collectMajorKey;
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

    @Generated(hash = 855346186)
    public Collect(String collectMajorKey, String collectImage, String collectName,
            String collectCT, long collectTime, String collectItemType,
            String collectType, boolean check) {
        this.collectMajorKey = collectMajorKey;
        this.collectImage = collectImage;
        this.collectName = collectName;
        this.collectCT = collectCT;
        this.collectTime = collectTime;
        this.collectItemType = collectItemType;
        this.collectType = collectType;
        this.check = check;
    }
    @Generated(hash = 1726975718)
    public Collect() {
    }

}
