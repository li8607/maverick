package com.maverick.presenter.implView;

import com.maverick.bean.CaricatureInfo;
import com.maverick.bean.SinaInfo;

import java.util.List;

/**
 * Created by limingfei on 2017/10/20.
 */
public interface ISinaItemFragmentView {

    void onShowSuccessView(List<SinaInfo> list, boolean isHasMore);

    void onShowEmptyView();

    void onShowErrorView();

    void onLoadMoreSuccess(List<SinaInfo> list, boolean isHasMore);

    void onLoadMoreFail();
}
