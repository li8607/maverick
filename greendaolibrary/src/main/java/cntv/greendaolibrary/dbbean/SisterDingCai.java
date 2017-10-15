package cntv.greendaolibrary.dbbean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/10/15.
 */
@Entity
public class SisterDingCai {
    @Id
    @Property(nameInDb = "DINGCAIID")
    private String dingCaiId;
    @Property(nameInDb = "DING")
    private boolean ding;
    @Property(nameInDb = "CAI")
    private boolean cai;
    public boolean getCai() {
        return this.cai;
    }
    public void setCai(boolean cai) {
        this.cai = cai;
    }
    public boolean getDing() {
        return this.ding;
    }
    public void setDing(boolean ding) {
        this.ding = ding;
    }
    public String getDingCaiId() {
        return this.dingCaiId;
    }
    public void setDingCaiId(String dingCaiId) {
        this.dingCaiId = dingCaiId;
    }
    @Generated(hash = 1854312202)
    public SisterDingCai(String dingCaiId, boolean ding, boolean cai) {
        this.dingCaiId = dingCaiId;
        this.ding = ding;
        this.cai = cai;
    }
    @Generated(hash = 2097034887)
    public SisterDingCai() {
    }
}
