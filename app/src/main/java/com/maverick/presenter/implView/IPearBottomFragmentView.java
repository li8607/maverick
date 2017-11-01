package com.maverick.presenter.implView;

import com.maverick.bean.PearVideoDetailInfoVideo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public interface IPearBottomFragmentView {

    void onShowSuccessView();

    void onShowEmptyView();

    void onShowErrorView();

    void onShowVideoView(List<PearVideoDetailInfoVideo> list);
}
