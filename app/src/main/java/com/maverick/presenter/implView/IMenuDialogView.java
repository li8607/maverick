package com.maverick.presenter.implView;

import com.maverick.bean.MenuItemInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/15.
 */
public interface IMenuDialogView {
    void showProgress(boolean show);

    void onShowShareView(List<MenuItemInfo> menuItemInfos);

    void onShowSendView(List<MenuItemInfo> send);
}
