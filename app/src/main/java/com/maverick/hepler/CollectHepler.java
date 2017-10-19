package com.maverick.hepler;

import com.maverick.bean.SisterInfo;
import com.maverick.type.CollectType;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/10/19.
 */
public class CollectHepler {

    public static Collect getCollect(SisterInfo sisterInfo) {

        if (sisterInfo == null) {
            return null;
        }

        Collect collect = new Collect();
        collect.setCollectType(CollectType.SISTER);
        collect.setCollectMajorKey(sisterInfo.getId());
        collect.setCollectTime(System.currentTimeMillis());
        collect.setCollectCT(sisterInfo.getCreate_time());
        collect.setCollectName(sisterInfo.getText());
        collect.setCollectImage(sisterInfo.getImage2());
        collect.setCollectItemType(sisterInfo.getType());
        return collect;
    }

}
