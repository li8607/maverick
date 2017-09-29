package com.maverick.presenter;

import android.content.Context;

import com.maverick.model.CollectModel;
import com.maverick.presenter.implView.ICollectItemBeautyFragmentView;

import java.util.ArrayList;
import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/9/29.
 */
public class CollectItemBeautyFragmentPresenter extends BasePresenter {

    private Context mContext;
    private ICollectItemBeautyFragmentView mView;

    public CollectItemBeautyFragmentPresenter(Context context, ICollectItemBeautyFragmentView view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void release() {

    }

    public void loadData() {
        List<Collect> list = CollectModel.newInstance().getBeautyData();
        if (list != null && list.size() > 0) {
            mView.onShowSuccessView(list);
        } else {
            mView.onShowEmptyView();
        }
    }
}
