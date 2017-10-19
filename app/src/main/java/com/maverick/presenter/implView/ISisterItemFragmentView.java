package com.maverick.presenter.implView;

import com.maverick.bean.SisterInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/30.
 */
public interface ISisterItemFragmentView {


    void onShowSuccessView(List<SisterInfo> beautyInfo);

    void onShowEmptyView();

    void onShowErrorView();

    void onLoadMoreSuccess(List<SisterInfo> beautyInfo, boolean isHasMore);

    void onLoadMoreFail();
}
