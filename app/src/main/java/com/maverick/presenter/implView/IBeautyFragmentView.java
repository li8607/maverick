package com.maverick.presenter.implView;

import com.maverick.bean.BeautyItemInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */
public interface IBeautyFragmentView {

    void onShowSuccessView(List<BeautyItemInfo> beautyInfo);

    void onShowEmptyView();

    void onShowErrorView();

    void onLoadMoreSuccess(List<BeautyItemInfo> beautyInfo, boolean isHasMore);

    void onLoadMoreFail();
}
