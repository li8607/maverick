package cntv.greendaolibrary.dbbean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by limingfei on 2017/9/27.
 */
@Entity
public class Collect {
    @Id
    private Long id;
    @Property(nameInDb = "COLLECTIMAGE")
    private String collectImage;
    @Property(nameInDb = "COLLECTNAME")
    private String collectName;
    @Property(nameInDb = "COLLECTTIME")
    private long collectTime;
    @Property(nameInDb = "COLLECTITEMTYPE")
    private String collectItemType;  // 1.文本；2.图片；3.动图;
    @Property(nameInDb = "COLLECTTYPE")
    private String collectType;  // 1.笑话；2.美女
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
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 25065693)
    public Collect(Long id, String collectImage, String collectName,
            long collectTime, String collectItemType, String collectType) {
        this.id = id;
        this.collectImage = collectImage;
        this.collectName = collectName;
        this.collectTime = collectTime;
        this.collectItemType = collectItemType;
        this.collectType = collectType;
    }
    @Generated(hash = 1726975718)
    public Collect() {
    }

}
