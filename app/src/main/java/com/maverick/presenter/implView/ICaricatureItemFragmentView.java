package com.maverick.presenter.implView;

import com.maverick.bean.CaricatureInfo;

import java.util.List;

/**
 * Created by limingfei on 2017/10/20.
 */
public interface ICaricatureItemFragmentView {

    void onShowSuccessView(List<CaricatureInfo> list, boolean isHasMore);

    void onShowEmptyView();

    void onShowErrorView();

    void onLoadMoreSuccess(List<CaricatureInfo> list, boolean isHasMore);

    void onLoadMoreFail();
}
