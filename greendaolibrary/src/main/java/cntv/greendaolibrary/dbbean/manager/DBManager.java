package cntv.greendaolibrary.dbbean.manager;

import android.content.Context;

import cntv.greendaolibrary.dbbean.CollectDao;
import cntv.greendaolibrary.dbbean.DaoMaster;
import cntv.greendaolibrary.dbbean.DaoSession;
import cntv.greendaolibrary.dbbean.HistoryDao;

/**
 * Created by limingfei on 2017/9/27.
 */
public class DBManager {

    private static DaoSession mDaoSession;

    public static void initGreenDao(Context context) {
        if(context == null) {
            return;
        }

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "maverick.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession(Context context) {
        if (mDaoSession == null) {
            initGreenDao(context);
        }
        return mDaoSession;
    }

    public static HistoryDao getHistoryDao(Context context) {
        DaoSession daoSession = getDaoSession(context);
        return daoSession == null ? null : daoSession.getHistoryDao();
    }

    public static CollectDao getCollectDao(Context context) {
        DaoSession daoSession = getDaoSession(context);
        return daoSession == null ? null : daoSession.getCollectDao();
    }
}
