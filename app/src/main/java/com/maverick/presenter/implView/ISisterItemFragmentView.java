package com.maverick.presenter.implView;

import com.maverick.bean.SisterInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/30.
 */
public interface ISisterItemFragmentView {


    void onShowSuccessView(List<SisterInfo> beautyInfo, boolean hasMore);

    void onShowEmptyView();

    void onShowErrorView();

    void onLoadMoreSuccess(List<SisterInfo> list, int positionStart, int count, boolean isHasMore);

    void onLoadMoreFail();
}
