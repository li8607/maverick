package com.maverick.presenter;

import android.content.Context;

import com.maverick.bean.CollectTabInfo;
import com.maverick.model.CollectModel;
import com.maverick.presenter.implView.ICollectItemFragmentView;

import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/10/5.
 */
public class CollectItemFragmentPresenter extends BasePresenter {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private ICollectItemFragmentView mView;

    public CollectItemFragmentPresenter(Context context, ICollectItemFragmentView view) {
        this.mContext = context;
        this.mView = view;
    }


    @Override
    public void release() {

    }

    public void loadData(CollectTabInfo collectTabInfo) {

        if (collectTabInfo == null) {
            mView.onShowEmptyView();
            return;
        }
        Collect collect = new Collect();
        collect.setCollectType(collectTabInfo.getCollectType());
        List<Collect> list = CollectModel.newInstance().getData(collect);

        if (list != null && list.size() > 0) {
            mView.onShowSuccessView(list);
        } else {
            mView.onShowEmptyView();
        }
    }
}
