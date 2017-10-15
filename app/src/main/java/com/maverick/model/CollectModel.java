package com.maverick.model;

import android.text.TextUtils;

import com.maverick.MainApp;
import com.maverick.imodel.ICollectModel;
import com.maverick.type.CollectType;

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
        List<Collect> list = mCollectDao.queryBuilder().where(CollectDao.Properties.CollectType.eq("2")).orderDesc(CollectDao.Properties.CollectTime).build().list();
        return list;
    }

    @Override
    public List<Collect> getJokeTextData() {
        List<Collect> list = mCollectDao.queryBuilder().where(CollectDao.Properties.CollectType.eq("1"), CollectDao.Properties.CollectItemType.eq("1")).orderDesc(CollectDao.Properties.CollectTime).build().list();
        return list;
    }

    @Override
    public List<Collect> getJokeImgData() {
        List<Collect> list = mCollectDao.queryBuilder().where(CollectDao.Properties.CollectType.eq("1"), CollectDao.Properties.CollectItemType.eq("2")).orderDesc(CollectDao.Properties.CollectTime).build().list();
        return list;
    }

    @Override
    public List<Collect> getJokeGifData() {
        List<Collect> list = mCollectDao.queryBuilder().where(CollectDao.Properties.CollectType.eq("1"), CollectDao.Properties.CollectItemType.eq("3")).orderDesc(CollectDao.Properties.CollectTime).build().list();
        return list;
    }

    @Override
    public List<Collect> getSisterData() {
        List<Collect> list = mCollectDao.queryBuilder().where(CollectDao.Properties.CollectType.eq(CollectType.SISTER)).orderDesc(CollectDao.Properties.CollectTime).build().list();
        return list;
    }

    @Override
    public boolean insertCollectDB(Collect collect) {
        if (collect == null || TextUtils.isEmpty(collect.getCollectMajorKey())) {
            return false;
        }
        boolean result = mCollectDao.insertOrReplace(collect) != -1;
        if (result) {
            onChange();
        }
        return result;
    }

    @Override
    public boolean deleteCollectDB(Collect collect) {

        if (collect == null || TextUtils.isEmpty(collect.getCollectMajorKey())) {
            return false;
        }

        boolean flag = false;
        try {
            //删除一条
            mCollectDao.delete(collect);
            flag = true;
            if (flag) {
                onChange();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean deleteCollectDBList(List<Collect> collects) {
        boolean flag = false;
        try {
            mCollectDao.deleteInTx(collects);
            flag = true;
            if (flag) {
                onChange();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean hasCollectDB(Collect collect) {
        if (collect == null || TextUtils.isEmpty(collect.getCollectMajorKey())) {
            return false;
        }

        List<Collect> list = mCollectDao.queryBuilder().where(CollectDao.Properties.CollectMajorKey.eq(collect.getCollectMajorKey())).build().list();
        return list != null && list.size() > 0;
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
