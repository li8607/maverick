package com.maverick.presenter.implView;

import com.maverick.bean.PearVideoInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public interface IPearItemFragmentView {

    void onShowSuccessView(List<PearVideoInfo> hotList, List<PearVideoInfo> list, boolean isHasMore);

    void onShowEmptyView();

    void onShowErrorView();

    void onLoadMoreSuccess(List<PearVideoInfo> list, boolean isHasMore);

    void onLoadMoreFail();
}
