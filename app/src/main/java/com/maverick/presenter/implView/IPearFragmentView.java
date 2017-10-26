package com.maverick.presenter.implView;

import com.maverick.bean.PearVideoTabInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public interface IPearFragmentView {
    void onShowSuccessView(List<PearVideoTabInfo> list);

    void onShowEmptyView();

    void onShowErrorView();
}
