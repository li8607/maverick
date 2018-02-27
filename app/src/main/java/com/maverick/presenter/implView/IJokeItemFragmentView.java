package com.maverick.presenter.implView;

import com.maverick.bean.GifInfo;

import java.util.List;

/**
 * Created by ll on 2017/5/22.
 */
public interface IJokeItemFragmentView {

    void onShowSuccessView(List<GifInfo> beautyInfo, boolean isHasMore);

    void onShowEmptyView();

    void onShowErrorView();

    void onLoadMoreSuccess(List<GifInfo> list, int positionStart, int count, boolean isHasMore);

    void onLoadMoreFail();
}
