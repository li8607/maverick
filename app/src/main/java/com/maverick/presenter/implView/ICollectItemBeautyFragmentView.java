package com.maverick.presenter.implView;

import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/9/29.
 */
public interface ICollectItemBeautyFragmentView {

    void onShowSuccessView(List<Collect> collects);

    void onShowEmptyView();
}
