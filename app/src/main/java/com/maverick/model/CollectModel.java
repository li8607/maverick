package com.maverick.model;

import com.maverick.MainApp;
import com.maverick.imodel.ICollectModel;

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
    public boolean insertCollectDB(Collect collect) {
        return mCollectDao.insertOrReplace(collect) != -1;
    }

    @Override
    public boolean deleteCollectDB(Collect collect) {
        boolean flag = false;
        try {
            //删除一条
            mCollectDao.delete(collect);
            flag = true;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
