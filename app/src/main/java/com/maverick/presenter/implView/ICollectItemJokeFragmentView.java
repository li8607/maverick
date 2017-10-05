package com.maverick.presenter.implView;

import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/10/5.
 */
public interface ICollectItemJokeFragmentView {

    void onShowSuccessView(List<Collect> collects);

    void onShowEmptyView();

}
