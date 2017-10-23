package com.maverick.presenter.implView;

import com.maverick.bean.CaricatureListInfo;

/**
 * Created by limingfei on 2017/10/23.
 */
public interface ICaricatureActivityView {
    void onShowSuccessView(CaricatureListInfo info);

    void onShowEmptyView();

    void onShowErrorView();

}
