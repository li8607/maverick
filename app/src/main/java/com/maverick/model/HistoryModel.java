package com.maverick.model;

import android.util.Log;

import com.maverick.MainApp;
import com.maverick.imodel.IHistoryModel;
import com.maverick.util.TimeUtils;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cntv.greendaolibrary.dbbean.History;
import cntv.greendaolibrary.dbbean.HistoryDao;
import cntv.greendaolibrary.dbbean.manager.DBManager;

/**
 * Created by limingfei on 2017/9/27.
 */
public class HistoryModel implements IHistoryModel {

    private String TAG = getClass().getSimpleName();
    private static HistoryDao mDao;
    private static HistoryModel mHistoryModel;

    public static HistoryModel newInstance() {
        synchronized (History.class) {
            if (mHistoryModel == null) {
                mHistoryModel = new HistoryModel();

            }
        }
        return mHistoryModel;
    }

    private HistoryModel() {
        mDao = DBManager.getHistoryDao(MainApp.mContext);
    }

    @Override
    public List<History> getHistory() {
        List<History> list = mDao.loadAll();
        return list;
    }

    @Override
    public List<History> getBeautyHistory() {
        List<History> list = mDao.queryBuilder().where(HistoryDao.Properties.HistoryType.eq("2")).build().list();
        return list;
    }

    @Override
    public List<History> getJokeHistory() {
        List<History> list = mDao.queryBuilder().where(HistoryDao.Properties.HistoryType.eq("1")).build().list();
        return list;
    }

    @Override
    public List<History> getTextHistory() {
        List<History> list = mDao.queryBuilder().where(HistoryDao.Properties.HistoryType.eq("1"), HistoryDao.Properties.HistoryItemType.eq("1")).build().list();
        return list;
    }

    @Override
    public List<History> getImgHistory() {
        List<History> list = mDao.queryBuilder().where(HistoryDao.Properties.HistoryType.eq("1"), HistoryDao.Properties.HistoryItemType.eq("2")).build().list();
        return list;
    }

    @Override
    public List<History> getGifHistory() {
        List<History> list = mDao.queryBuilder().where(HistoryDao.Properties.HistoryType.eq("1"), HistoryDao.Properties.HistoryItemType.eq("3")).build().list();
        return list;
    }

    @Override
    public List<History> getTodayHistory() {
        long zero = TimeUtils.getStartTimeOfDay(new Date());
        Log.e(TAG, "getTodayHistory zero = " + zero);
        List<History> list = mDao.queryBuilder().where(HistoryDao.Properties.HistoryTime.gt(zero)).orderDesc(HistoryDao.Properties.HistoryTime).build().list();
        return list;
    }

    @Override
    public List<History> getSevenDaysHistory() {
        long today = TimeUtils.getStartTimeOfDay(new Date());
        long zero = TimeUtils.getSevenDayStartTimeOfDay();
        Log.e(TAG, "getSevenDaysHistory zero = " + zero);
        List<History> list = mDao.queryBuilder().where(HistoryDao.Properties.HistoryTime.between(zero, today)).orderDesc(HistoryDao.Properties.HistoryTime).build().list();
        return list;
    }

    @Override
    public List<History> getEarlierHistory() {
        long zero = TimeUtils.getSevenDayStartTimeOfDay();
        List<History> list = mDao.queryBuilder().where(HistoryDao.Properties.HistoryTime.lt(zero)).orderDesc(HistoryDao.Properties.HistoryTime).build().list();
        return list;
    }

    @Override
    public boolean insertHistoryDB(History history) {

        List<History> list = mDao.queryBuilder().where(HistoryDao.Properties.Historyimage.eq(history.getHistoryimage())).orderDesc(HistoryDao.Properties.HistoryTime).build().list();

        if(list.size() > 0) {

        }


        return mDao.insertOrReplace(history) != -1;
    }

    @Override
    public boolean deleteHistoryDB(History history) {
        boolean flag = false;
        try {
            //删除一条
            mDao.delete(history);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean deleteHistoryDBList(List<History> history) {
        boolean flag = false;
        try {
            mDao.deleteInTx(history);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean deleteHistoryDBAll() {
        boolean flag = false;
        try {
            //删除所有的
            mDao.deleteAll();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean containsHistoryDB(History history) {
        return mDao.hasKey(history);
    }
}
