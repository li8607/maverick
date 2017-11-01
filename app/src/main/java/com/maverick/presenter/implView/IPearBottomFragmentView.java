package com.maverick.presenter.implView;

import com.maverick.bean.PearItemInfo;
import com.maverick.bean.PearVideoDetailInfoVideo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public interface IPearBottomFragmentView {

    void onShowSuccessView(List<PearItemInfo> list);

    void onShowEmptyView();

    void onShowErrorView();

    void onShowVideoView(List<PearVideoDetailInfoVideo> list);
}
