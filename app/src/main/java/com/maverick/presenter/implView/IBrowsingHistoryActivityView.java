package com.maverick.presenter.implView;

import com.maverick.bean.GifInfo;

import java.util.List;

import cntv.greendaolibrary.dbbean.History;

/**
 * Created by Administrator on 2017/9/27.
 */
public interface IBrowsingHistoryActivityView {

    void onShowSuccessView(List<History> histories);

    void onShowEmptyView();
}
