package com.maverick.hepler;

import android.text.TextUtils;

import com.maverick.bean.BeautyItemInfo;
import com.maverick.bean.BigImgInfo;
import com.maverick.bean.CaricatureInfo;
import com.maverick.bean.GifInfo;
import com.maverick.bean.SisterInfo;
import com.maverick.bean.WebDetailInfo;
import com.maverick.global.Tag;
import com.maverick.type.CollectType;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/10/19.
 */
public class BeanHelper {

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
        collect.setCollectUserId(Tag.USER_ID);

        if (TextUtils.equals(sisterInfo.getType(), Tag.SISTER_TEXT)) {
            collect.setCollectItemType(CollectType.SISTER_TEXT);
        } else if (TextUtils.equals(sisterInfo.getType(), Tag.SISTER_IMAGE)) {
            collect.setCollectItemType(CollectType.SISTER_IMAGE);
        } else if (TextUtils.equals(sisterInfo.getType(), Tag.SISTER_AUDIO)) {
            collect.setCollectItemType(CollectType.SISTER_AUDIO);
        } else if (TextUtils.equals(sisterInfo.getType(), Tag.SISTER_VIDEO)) {
            collect.setCollectItemType(CollectType.SISTER_VIDEO);
        }
        return collect;
    }

    public static Collect getCollect(GifInfo info) {

        if (info == null) {
            return null;
        }

        Collect collect = new Collect();
        collect.setCollectTime(System.currentTimeMillis());
        collect.setCollectType(CollectType.JOKE);
        collect.setCollectImage(info.getImg());
        collect.setCollectCT(info.getCt());
        collect.setCollectUserId(Tag.USER_ID);
        if (TextUtils.equals(info.getType(), Tag.JOKE_TEXT)) {
            collect.setCollectItemType(CollectType.JOKE_TEXT);
            collect.setCollectName(info.getText());
            collect.setCollectMajorKey(info.getText());
        } else if (TextUtils.equals(info.getType(), Tag.JOKE_IMG)) {
            collect.setCollectItemType(CollectType.JOKE_IMAGE);
            collect.setCollectMajorKey(info.getImg());
            collect.setCollectName(info.getTitle());
        } else if (TextUtils.equals(info.getType(), Tag.JOKE_GIF)) {
            collect.setCollectItemType(CollectType.JOKE_GIF);
            collect.setCollectName(info.getTitle());
            collect.setCollectMajorKey(info.getImg());
        }
        return collect;
    }

    public static Collect getCollect(BeautyItemInfo info) {

        if (info == null) {
            return null;
        }

        Collect collect = new Collect();
        collect.setCollectType(CollectType.BEAUTY);
        collect.setCollectImage(info.getUrl());
        collect.setCollectMajorKey(info.getUrl());
        collect.setCollectTime(System.currentTimeMillis());
        collect.setCollectUserId(Tag.USER_ID);
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

    public static WebDetailInfo getWebDetailInfo(CaricatureInfo info) {

        if (info == null) {
            return null;
        }

        WebDetailInfo webDetailInfo = new WebDetailInfo();
        webDetailInfo.setWebUrl(info.getLink());
        webDetailInfo.setTitle(info.getTitle());
        if (info.getThumbnailList() != null && info.getThumbnailList().size() > 0) {
            webDetailInfo.setImageUrl(info.getThumbnailList().get(0));
        }
        return webDetailInfo;
    }

    public static WebDetailInfo getWebDetailInfo(SisterInfo info) {

        if (info == null) {
            return null;
        }

        WebDetailInfo webDetailInfo = new WebDetailInfo();
        webDetailInfo.setWebUrl(info.getWeixin_url());
        webDetailInfo.setTitle(info.getText());
        webDetailInfo.setImageUrl(info.getImage2());
        return webDetailInfo;
    }

}
