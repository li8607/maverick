package com.maverick.model;

import android.text.TextUtils;

import com.maverick.MainApp;

import java.util.List;

import cntv.greendaolibrary.dbbean.SisterDingCai;
import cntv.greendaolibrary.dbbean.SisterDingCaiDao;
import cntv.greendaolibrary.dbbean.manager.DBManager;

/**
 * Created by Administrator on 2017/10/15.
 */
public class SisterDingCaiModel {

    private static SisterDingCaiModel mSisterDingCaiModel;
    private final SisterDingCaiDao mSisterDingCaiDao;

    private SisterDingCaiModel() {
        this.mSisterDingCaiDao = DBManager.getSisterDingCai(MainApp.mContext);
    }

    public static SisterDingCaiModel newInstance() {
        synchronized (SisterDingCaiModel.class) {
            if (mSisterDingCaiModel == null) {
                mSisterDingCaiModel = new SisterDingCaiModel();

            }
        }
        return mSisterDingCaiModel;
    }

    public SisterDingCai getSisterDingCai(SisterDingCai sisterDingCai) {
        if (sisterDingCai == null) {
            return null;
        }
        SisterDingCai dingCai = mSisterDingCaiDao.queryBuilder().where(SisterDingCaiDao.Properties.DingCaiId.eq(sisterDingCai.getDingCaiId())).unique();
        return dingCai;
    }

    public boolean insertSisterDingCaiDB(SisterDingCai sisterDingCai) {
        if (sisterDingCai == null || TextUtils.isEmpty(sisterDingCai.getDingCaiId())) {
            return false;
        }
        return mSisterDingCaiDao.insertOrReplace(sisterDingCai) != -1;
    }

    public boolean deleteSisterDingCaiDB(SisterDingCai sisterDingCai) {

        if (sisterDingCai == null || TextUtils.isEmpty(sisterDingCai.getDingCaiId())) {
            return false;
        }

        boolean flag = false;
        try {
            //删除一条
            mSisterDingCaiDao.delete(sisterDingCai);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
