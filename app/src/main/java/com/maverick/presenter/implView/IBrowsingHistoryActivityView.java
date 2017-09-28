package com.maverick.presenter.implView;

import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 */
public interface IBrowsingHistoryActivityView {

    void onShowSuccessView(List<Object> histories);

    void onShowEmptyView();
}
