package com.maverick.model;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.maverick.MainApp;
import com.maverick.global.Tag;
import com.maverick.imodel.ICollectModel;
import com.maverick.type.CollectType;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;
import cntv.greendaolibrary.dbbean.CollectDao;
import cntv.greendaolibrary.dbbean.manager.DBManager;

/**
 * Created by limingfei on 2017/9/29.
 */
public class CollectModel implements ICollectModel {

    private static CollectModel mCollectModel;
    private CollectDao mCollectDao;

    public static CollectModel newInstance() {
        synchronized (CollectModel.class) {
            if (mCollectModel == null) {
                mCollectModel = new CollectModel();

            }
        }
        return mCollectModel;
    }

    private CollectModel() {
        this.mCollectDao = DBManager.getCollectDao(MainApp.mContext);
    }

    @Override
    public List<Collect> getBeautyData() {
        List<Collect> list = mCollectDao.queryBuilder().where(
                CollectDao.Properties.CollectUserId.eq(Tag.USER_ID)
                , CollectDao.Properties.CollectType.eq(CollectType.BEAUTY)).orderDesc(CollectDao.Properties.CollectTime).build().list();
        return list;
    }

    @Override
    public List<Collect> getJokeTextData() {
        List<Collect> list = mCollectDao.queryBuilder().where(
                CollectDao.Properties.CollectUserId.eq(Tag.USER_ID)
                , CollectDao.Properties.CollectType.eq(CollectType.JOKE)
                , CollectDao.Properties.CollectItemType.eq(CollectType.JOKE_TEXT)).orderDesc(CollectDao.Properties.CollectTime).build().list();
        return list;
    }

    @Override
    public List<Collect> getJokeImgData() {
        List<Collect> list = mCollectDao.queryBuilder().where(
                CollectDao.Properties.CollectUserId.eq(Tag.USER_ID)
                , CollectDao.Properties.CollectType.eq(CollectType.JOKE)
                , CollectDao.Properties.CollectItemType.eq(CollectType.JOKE_IMAGE)).orderDesc(CollectDao.Properties.CollectTime).build().list();
        return list;
    }

    @Override
    public List<Collect> getJokeGifData() {
        List<Collect> list = mCollectDao.queryBuilder().where(
                CollectDao.Properties.CollectUserId.eq(Tag.USER_ID)
                , CollectDao.Properties.CollectType.eq(CollectType.JOKE)
                , CollectDao.Properties.CollectItemType.eq(CollectType.JOKE_GIF)).orderDesc(CollectDao.Properties.CollectTime).build().list();
        return list;
    }

    @Override
    public List<Collect> getSisterData() {
        List<Collect> list = mCollectDao.queryBuilder().where(
                CollectDao.Properties.CollectUserId.eq(Tag.USER_ID)
                , CollectDao.Properties.CollectType.eq(CollectType.SISTER)).orderDesc(CollectDao.Properties.CollectTime).build().list();
        return list;
    }

    @Override
    public List<Collect> getCaricatureData() {
        List<Collect> list = mCollectDao.queryBuilder().where(
                CollectDao.Properties.CollectUserId.eq(Tag.USER_ID)
                , CollectDao.Properties.CollectType.eq(CollectType.CARICATURE)).orderDesc(CollectDao.Properties.CollectTime).build().list();
        return list;
    }

    @Override
    public List<Collect> getSinaData() {
        List<Collect> list = mCollectDao.queryBuilder().where(
                CollectDao.Properties.CollectUserId.eq(Tag.USER_ID)
                , CollectDao.Properties.CollectType.eq(CollectType.SINA)).orderDesc(CollectDao.Properties.CollectTime).build().list();
        return list;
    }

    @Override
    public boolean insertCollectDB(Collect collect) {

        if (collect == null) {
            return false;
        }

        if (hasCollectDB(collect)) {
            return true;
        }
        long insert = mCollectDao.insert(collect);
        if (insert != -1) {
            onChange();
        }
        return insert != -1;
    }

    @Override
    public boolean deleteCollectDB(Collect collect) {
        if (collect == null) {
            return false;
        } else if (mCollectDao.getKey(collect) != null) {
            mCollectDao.delete(collect);
            onChange();
            return true;
        } else {
            DeleteQuery<Collect> sub = mCollectDao.queryBuilder().where(
                    CollectDao.Properties.CollectUserId.eq(Tag.USER_ID)
                    , getWhereCondition(collect)).buildDelete();
            if (sub != null) {
                sub.executeDeleteWithoutDetachingEntities();
                onChange();
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean deleteCollectDBList(List<Collect> collects) {

        if (collects == null) {
            return false;
        }

        for (int i = 0; i < collects.size(); i++) {
            deleteCollectDB(collects.get(i));
        }
        onChange();
        return true;
    }

    @Override
    public boolean hasCollectDB(Collect collect) {
        if (collect == null) {
            return false;
        }

        WhereCondition whereCondition = getWhereCondition(collect);

        if (whereCondition == null) {
            return false;
        }

        List<Collect> list = mCollectDao.queryBuilder().where(
                CollectDao.Properties.CollectUserId.eq(Tag.USER_ID)
                , whereCondition).build().list();
        return list != null && list.size() > 0;
    }

    @Nullable
    private WhereCondition getWhereCondition(Collect collect) {

        String type = collect.getCollectType();
        String itemType = collect.getCollectItemType();

        WhereCondition whereCondition = null;

        if (TextUtils.equals(type, CollectType.JOKE)) {

            if (TextUtils.equals(itemType, CollectType.JOKE_TEXT)) {
                whereCondition = CollectDao.Properties.CollectName.eq(collect.getCollectName());
            } else if (TextUtils.equals(itemType, CollectType.JOKE_IMAGE) || TextUtils.equals(itemType, CollectType.JOKE_GIF)) {
                whereCondition = CollectDao.Properties.CollectImage.eq(collect.getCollectImage());
            }

        } else if (TextUtils.equals(type, CollectType.BEAUTY)) {

            whereCondition = CollectDao.Properties.CollectImage.eq(collect.getCollectImage());

        } else if (TextUtils.equals(type, CollectType.SISTER)) {

            whereCondition = CollectDao.Properties.CollectId.eq(collect.getCollectId());
        } else if (TextUtils.equals(type, CollectType.CARICATURE)) {

            whereCondition = CollectDao.Properties.CollectUrl.eq(collect.getCollectUrl());
        } else if (TextUtils.equals(type, CollectType.SINA)) {

            whereCondition = CollectDao.Properties.CollectUrl.eq(collect.getCollectUrl());
        } else if (TextUtils.equals(type, CollectType.PEAR)) {

            whereCondition = CollectDao.Properties.CollectId.eq(collect.getCollectId());
        }
        return whereCondition;
    }

    public void onChange() {
        if (mList != null && mList.size() > 0) {
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).onChange();
            }
        }
    }

    private List<OnCollectListener> mList = new ArrayList<>();

    public void addOnCollectListener(OnCollectListener listener) {
        if (listener != null) {
            mList.add(listener);
        }
    }

    public void removeOnCollectListener(OnCollectListener listener) {
        if (listener != null) {
            mList.remove(listener);
        }
    }

    public void removeAllOnCollectListener() {
        mList.clear();
    }

    public interface OnCollectListener {
        void onChange();
    }
}
