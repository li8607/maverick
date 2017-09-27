package cntv.greendaolibrary.dbbean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by limingfei on 2017/9/27.
 */
@Entity
public class History {
    @Id
    private Long id;
    @Property(nameInDb = "HISTORYIMAGE")
    private String historyimage;
    @Property(nameInDb = "HISTORYNAME")
    private String historyName;
    @Property(nameInDb = "HISTORYTIME")
    private long historyTime;
    @Property(nameInDb = "HISTORYITEMTYPE")
    private String historyItemType;  // 1.文本；2.图片；3.动图;
    @Property(nameInDb = "HISTORYTYPE")
    private String historyType;  // 1.笑话；2.美女
    public String getHistoryType() {
        return this.historyType;
    }
    public void setHistoryType(String historyType) {
        this.historyType = historyType;
    }
    public String getHistoryItemType() {
        return this.historyItemType;
    }
    public void setHistoryItemType(String historyItemType) {
        this.historyItemType = historyItemType;
    }
    public long getHistoryTime() {
        return this.historyTime;
    }
    public void setHistoryTime(long historyTime) {
        this.historyTime = historyTime;
    }
    public String getHistoryName() {
        return this.historyName;
    }
    public void setHistoryName(String historyName) {
        this.historyName = historyName;
    }
    public String getHistoryimage() {
        return this.historyimage;
    }
    public void setHistoryimage(String historyimage) {
        this.historyimage = historyimage;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 248218567)
    public History(Long id, String historyimage, String historyName,
            long historyTime, String historyItemType, String historyType) {
        this.id = id;
        this.historyimage = historyimage;
        this.historyName = historyName;
        this.historyTime = historyTime;
        this.historyItemType = historyItemType;
        this.historyType = historyType;
    }
    @Generated(hash = 869423138)
    public History() {
    }
}
