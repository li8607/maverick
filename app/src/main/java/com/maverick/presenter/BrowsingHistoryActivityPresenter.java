package com.maverick.presenter;

import android.content.Context;
import android.util.Log;

import com.maverick.model.HistoryModel;
import com.maverick.presenter.implView.IBrowsingHistoryActivityView;

import java.util.List;

import cntv.greendaolibrary.dbbean.History;

/**
 * Created by limingfei on 2017/9/27.
 */
public class BrowsingHistoryActivityPresenter extends BasePresenter {

    private String TAG = getClass().getSimpleName();
    private Context mContext;
    private IBrowsingHistoryActivityView mView;

    public BrowsingHistoryActivityPresenter(Context context, IBrowsingHistoryActivityView view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void release() {

    }

    public void loadData() {
//        List<History> list = HistoryModel.newInstance().getBeautyHistory();
//        if (list != null && list.size() > 0) {
//            mView.onShowSuccessView(list);
//        } else {
//            mView.onShowEmptyView();
//        }

        List<History> list = HistoryModel.newInstance().getTodayHistory();
        Log.e(TAG, "getTodayHistory = " + list);
        List<History> list2 = HistoryModel.newInstance().getSevenDaysHistory();
        Log.e(TAG, "getSevenDaysHistory = " + list2);
        List<History> list3 = HistoryModel.newInstance().getEarlierHistory();
        Log.e(TAG, "getEarlierHistory = " + list3);

    }
}
