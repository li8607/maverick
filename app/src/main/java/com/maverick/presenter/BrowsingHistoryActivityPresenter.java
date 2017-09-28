package com.maverick.presenter;

import android.content.Context;
import android.util.Log;

import com.maverick.model.HistoryModel;
import com.maverick.presenter.implView.IBrowsingHistoryActivityView;

import java.util.ArrayList;
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

        List<Object> list = new ArrayList<>();
        List<History> today = HistoryModel.newInstance().getTodayHistory();
        Log.e(TAG, "today = " + today);
        if(today != null && today.size() > 0) {
            String str = "今天";
            list.add(str);
            list.addAll(today);
        }
        List<History> sevenDay = HistoryModel.newInstance().getSevenDaysHistory();
        if(sevenDay != null && sevenDay.size() > 0) {
            String str = "七天内";
            list.add(str);
            list.addAll(sevenDay);
        }
        Log.e(TAG, "sevenDay = " + sevenDay);
        List<History> earlier = HistoryModel.newInstance().getEarlierHistory();
        if(earlier != null && earlier.size() > 0) {
            String str = "更早以前";
            list.add(str);
            list.addAll(earlier);
        }
        Log.e(TAG, "earlier = " + earlier);
        if (list != null && list.size() > 0) {
            mView.onShowSuccessView(list);
        } else {
            mView.onShowEmptyView();
        }
    }
}
