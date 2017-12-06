package com.maverick.hepler;

import android.text.TextUtils;

import com.maverick.bean.BeautyItemInfo;
import com.maverick.bean.BigImgInfo;
import com.maverick.bean.CaricatureDetailInfo;
import com.maverick.bean.CaricatureInfo;
import com.maverick.bean.GifInfo;
import com.maverick.bean.MenuDetailInfo;
import com.maverick.bean.PearVideoDetailBean;
import com.maverick.bean.PearVideoInfo;
import com.maverick.bean.SinaInfo;
import com.maverick.bean.SisterInfo;
import com.maverick.bean.WebDetailInfo;
import com.maverick.global.Tag;
import com.maverick.type.CollectType;
import com.maverick.type.ShareType;

import cntv.greendaolibrary.dbbean.Collect;
import cntv.greendaolibrary.dbbean.History;

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
        collect.setCollectTime(System.currentTimeMillis());
        collect.setCollectCT(sisterInfo.getCreate_time());
        collect.setCollectName(sisterInfo.getText());
        collect.setCollectImage(sisterInfo.getImage2());
        collect.setCollectId(sisterInfo.getId());
        collect.setCollectUrl(sisterInfo.getWeixin_url());
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
        } else if (TextUtils.equals(info.getType(), Tag.JOKE_IMG)) {
            collect.setCollectItemType(CollectType.JOKE_IMAGE);
            collect.setCollectName(info.getTitle());
        } else if (TextUtils.equals(info.getType(), Tag.JOKE_GIF)) {
            collect.setCollectItemType(CollectType.JOKE_GIF);
            collect.setCollectName(info.getTitle());
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
        collect.setCollectTime(System.currentTimeMillis());
        collect.setCollectUserId(Tag.USER_ID);
        return collect;
    }

    public static Collect getCollect(CaricatureInfo info) {

        if (info == null) {
            return null;
        }

        Collect collect = new Collect();
        collect.setCollectType(CollectType.CARICATURE);
        if (info.getThumbnailList() != null && info.getThumbnailList().size() > 0) {
            collect.setCollectImage(info.getThumbnailList().get(0));
        }
        collect.setCollectUrl(info.getLink());
        collect.setCollectName(info.getTitle());
        collect.setCollectTime(System.currentTimeMillis());
        collect.setCollectId(info.getId());
        collect.setCollectUserId(Tag.USER_ID);
        return collect;
    }

    public static Collect getCollect(SinaInfo info) {

        if (info == null) {
            return null;
        }

        Collect collect = new Collect();
        collect.setCollectType(CollectType.SINA);
        collect.setCollectName(info.getNewinfo());
        collect.setCollectImage(info.getImg());
        collect.setCollectCT(info.getDate());
        collect.setCollectUrl(info.getUrl());
        collect.setCollectTime(System.currentTimeMillis());
        collect.setCollectUserId(Tag.USER_ID);
        return collect;
    }

    public static History getHistory(PearVideoDetailBean info) {

        if (info == null) {
            return null;
        }

        History history = new History();
        history.setHistoryimage(info.getPic());
        history.setHistoryName(info.getName());
        history.setHistoryTime(System.currentTimeMillis());
        history.setHistoryContId(info.getContId());
        history.setHistoryUserId(Tag.USER_ID);
        return history;
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

    public static WebDetailInfo getWebDetailInfo(SinaInfo info) {

        if (info == null) {
            return null;
        }

        WebDetailInfo webDetailInfo = new WebDetailInfo();
        webDetailInfo.setWebUrl(info.getUrl());
        webDetailInfo.setTitle(info.getNewinfo());
        webDetailInfo.setImageUrl(info.getImg());
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

    public static WebDetailInfo getWebDetailInfo(Collect info) {

        if (info == null) {
            return null;
        }

        WebDetailInfo webDetailInfo = new WebDetailInfo();
        webDetailInfo.setWebUrl(info.getCollectUrl());
        webDetailInfo.setTitle(info.getCollectName());
        webDetailInfo.setImageUrl(info.getCollectImage());
        return webDetailInfo;
    }

    public static MenuDetailInfo getMenuDetailInfo(CaricatureInfo info) {

        if (info == null) {
            return null;
        }

        MenuDetailInfo menuDetailInfo = new MenuDetailInfo();
        menuDetailInfo.setShareType(ShareType.IMAGE_TEXT);
        menuDetailInfo.setWeburl(info.getLink());
        menuDetailInfo.setTitle(info.getTitle());
        if (info.getThumbnailList() != null && info.getThumbnailList().size() > 0) {
            menuDetailInfo.setImageurl(info.getThumbnailList().get(0));
        }
        return menuDetailInfo;
    }

    public static CaricatureDetailInfo getCaricatureDetailInfo(CaricatureInfo info) {
        if (info == null) {
            return null;
        }


        CaricatureDetailInfo caricatureDetailInfo = new CaricatureDetailInfo();
        caricatureDetailInfo.setId(info.getId());
        return caricatureDetailInfo;
    }

    public static CaricatureDetailInfo getCaricatureDetailInfo(Collect collect) {
        if (collect == null) {
            return null;
        }

        CaricatureDetailInfo caricatureDetailInfo = new CaricatureDetailInfo();
        caricatureDetailInfo.setId(collect.getCollectId());
        caricatureDetailInfo.setTitle(collect.getCollectName());
        return caricatureDetailInfo;
    }

    //        public static CaricatureDetailInfo getCaricatureDetailInfo(SinaInfo info) {
//        CaricatureDetailInfo caricatureDetailInfo = new CaricatureDetailInfo();
//        caricatureDetailInfo.setId(info.getCollectId());
//        caricatureDetailInfo.setTitle(info.getCollectName());
//        return caricatureDetailInfo;
//    }
    public static PearVideoDetailBean getPearVideoDetailBean(PearVideoInfo info) {

        if (info == null) {
            return null;
        }

        PearVideoDetailBean pearVideoDetailBean = new PearVideoDetailBean();
        pearVideoDetailBean.setContId(info.getContId());
        pearVideoDetailBean.setName(info.getName());
        pearVideoDetailBean.setPic(info.getPic());
        return pearVideoDetailBean;
    }

    public static PearVideoDetailBean getPearVideoDetailBean(History info) {

        if (info == null) {
            return null;
        }

        PearVideoDetailBean pearVideoDetailBean = new PearVideoDetailBean();
        pearVideoDetailBean.setContId(info.getHistoryContId());
        pearVideoDetailBean.setName(info.getHistoryName());
        pearVideoDetailBean.setPic(info.getHistoryimage());
        return pearVideoDetailBean;
    }
}
