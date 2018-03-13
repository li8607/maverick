package com.maverick.presenter.implView;

import com.maverick.bean.CollectTabInfo;

import java.util.List;

/**
 * Created by limingfei on 2018/3/13.
 */

public interface ICollectFragmentView {

    void onShowSuccessView(List<CollectTabInfo> list);

    void onShowEmptyView();

}
