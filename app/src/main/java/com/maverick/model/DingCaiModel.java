package com.maverick.model;

import android.text.TextUtils;

import com.maverick.MainApp;

import cntv.greendaolibrary.dbbean.DingCai;
import cntv.greendaolibrary.dbbean.DingCaiDao;
import cntv.greendaolibrary.dbbean.manager.DBManager;

/**
 * Created by Administrator on 2017/10/15.
 */
public class DingCaiModel {

    private static DingCaiModel mDingCaiModel;
    private final DingCaiDao mSisterDingCaiDao;

    private DingCaiModel() {
        this.mSisterDingCaiDao = DBManager.getDingCaiDao(MainApp.mContext);
    }

    public static DingCaiModel newInstance() {
        synchronized (DingCaiModel.class) {
            if (mDingCaiModel == null) {
                mDingCaiModel = new DingCaiModel();

            }
        }
        return mDingCaiModel;
    }

    public DingCai getSisterDingCai(DingCai sisterDingCai) {
        if (sisterDingCai == null) {
            return null;
        }
        DingCai dingCai = mSisterDingCaiDao.queryBuilder().where(DingCaiDao.Properties.DingCaiId.eq(sisterDingCai.getDingCaiId())).unique();
        return dingCai;
    }

    public boolean insertSisterDingCaiDB(DingCai dingCai) {
        if (dingCai == null || TextUtils.isEmpty(dingCai.getDingCaiId())) {
            return false;
        }
        return mSisterDingCaiDao.insertOrReplace(dingCai) != -1;
    }

    public boolean deleteSisterDingCaiDB(DingCai dingCai) {

        if (dingCai == null || TextUtils.isEmpty(dingCai.getDingCaiId())) {
            return false;
        }

        boolean flag = false;
        try {
            //删除一条
            mSisterDingCaiDao.delete(dingCai);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
