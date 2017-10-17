package com.maverick.presenter.implView;

import com.maverick.bean.ShareItemInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/15.
 */
public interface IShareDialogView {
    void showProgress(boolean show);

    void onShowShareView(List<ShareItemInfo> shareItemInfos);
}
