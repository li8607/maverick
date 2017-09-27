package com.maverick.presenter;

import android.util.Log;

import com.maverick.model.HistoryModel;

import java.util.List;

import cntv.greendaolibrary.dbbean.History;

/**
 * Created by limingfei on 2017/9/27.
 */
public class BrowsingHistoryActivityPresenter extends BasePresenter {

    private String TAG = getClass().getSimpleName();

    public BrowsingHistoryActivityPresenter() {

    }

    @Override
    public void release() {

    }

    public void loadData() {
        List<History> list = HistoryModel.newInstance().getBeautyHistory();
        Log.e(TAG, "list = " + list);
    }
}
