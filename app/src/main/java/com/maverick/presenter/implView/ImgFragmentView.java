package com.maverick.presenter.implView;

import android.content.Context;

import com.maverick.bean.GifInfo;
import com.maverick.presenter.ImgFragmentPresenter;

import java.util.List;

/**
 * Created by ll on 2017/5/22.
 */
public interface ImgFragmentView {

    void onShowSuccessView(List<GifInfo> beautyInfo);

    void onShowEmptyView();

    void onShowErrorView();

    void onLoadMoreSuccess(List<GifInfo> beautyInfo, boolean isHasMore);

    void onLoadMoreFail();
}
