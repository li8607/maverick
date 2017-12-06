package cntv.greendaolibrary.dbbean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by limingfei on 2017/9/27.
 */
@Entity
public class History {
    @Id
    @Property(nameInDb = "HISTORYIMAGE")
    private String historyimage;
    @Property(nameInDb = "HISTORYNAME")
    private String historyName;
    @Property(nameInDb = "HISTORYTIME")
    private long historyTime;
    @Property(nameInDb = "HISTORYITEMTYPE")
    private String historyItemType;  // 1.文本；2.图片；3.动图;
    @Property(nameInDb = "HISTORYTYPE")
    private String historyType;  // 1.笑话；2.美女;3.梨视频
    @Property(nameInDb = "HISTORYUSERID")
    private String historyUserId;  // 1.用户Id
    @Property(nameInDb = "HISTORYCONTID")
    private String historyContId;  // 1.梨视频ID
    @Property(nameInDb = "HISTORYVIDEOPOSITION")
    private long historyVideoPosition;  // 视频进度
    @Transient
    private boolean isCheck;  // 1.笑话；2.美女

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

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getHistoryUserId() {
        return this.historyUserId;
    }

    public void setHistoryUserId(String historyUserId) {
        this.historyUserId = historyUserId;
    }

    public long getHistoryVideoPosition() {
        return this.historyVideoPosition;
    }

    public void setHistoryVideoPosition(long historyVideoPosition) {
        this.historyVideoPosition = historyVideoPosition;
    }

    public String getHistoryContId() {
        return this.historyContId;
    }

    public void setHistoryContId(String historyContId) {
        this.historyContId = historyContId;
    }

    @Generated(hash = 1402456994)
    public History(String historyimage, String historyName, long historyTime,
                   String historyItemType, String historyType, String historyUserId,
                   String historyContId, long historyVideoPosition) {
        this.historyimage = historyimage;
        this.historyName = historyName;
        this.historyTime = historyTime;
        this.historyItemType = historyItemType;
        this.historyType = historyType;
        this.historyUserId = historyUserId;
        this.historyContId = historyContId;
        this.historyVideoPosition = historyVideoPosition;
    }

    @Generated(hash = 869423138)
    public History() {
    }
}
