package com.maverick.presenter.implView;

import com.maverick.bean.GifInfo;

import java.util.List;

/**
 * Created by ll on 2017/5/22.
 */
public interface TextFragmentView {

    void onShowSuccessView(List<GifInfo> beautyInfo);

    void onShowEmptyView();

    void onShowErrorView();

    void onLoadMoreSuccess(List<GifInfo> beautyInfo, boolean isHasMore);

    void onLoadMoreFail();
}
