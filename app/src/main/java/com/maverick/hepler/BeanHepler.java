package com.maverick.hepler;

import com.maverick.bean.BigImgInfo;
import com.maverick.bean.CaricatureInfo;
import com.maverick.bean.SisterInfo;
import com.maverick.type.CollectType;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/10/19.
 */
public class BeanHepler {

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

    public static BigImgInfo getBigImgInfo(CaricatureInfo info) {

        if (info == null) {
            return null;
        }

        BigImgInfo bigImgInfo = new BigImgInfo();
        bigImgInfo.setImgs(info.getThumbnailList());
        bigImgInfo.setImg(info.getThumbnailList().get(0));
        bigImgInfo.setWebUrl(info.getLink());
        bigImgInfo.setTitle(info.getTitle());
//        collect.setCollectType(CollectType.SISTER);
//        collect.setCollectMajorKey(sisterInfo.getId());
//        collect.setCollectTime(System.currentTimeMillis());
//        collect.setCollectCT(sisterInfo.getCreate_time());
//        collect.setCollectName(sisterInfo.getText());
//        collect.setCollectImage(sisterInfo.getImage2());
//        collect.setCollectItemType(sisterInfo.getType());
        return bigImgInfo;
    }

}
