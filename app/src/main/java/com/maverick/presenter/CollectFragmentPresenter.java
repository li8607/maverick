package com.maverick.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.maverick.R;
import com.maverick.bean.CollectTabInfo;
import com.maverick.model.CollectModel;
import com.maverick.presenter.implView.ICollectFragmentView;
import com.maverick.type.CollectType;

import java.util.ArrayList;
import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2018/3/13.
 */

public class CollectFragmentPresenter extends BasePresenter {

    private Context mContext;
    private ICollectFragmentView mView;
    private final CollectModel mCollectModel;
    private String[] mCollectTypes = new String[]{CollectType.SISTER, CollectType.CARICATURE};

    public CollectFragmentPresenter(Context context, ICollectFragmentView view) {
        this.mContext = context;
        this.mView = view;
        mCollectModel = CollectModel.newInstance();
    }

    @Override
    public void release() {

    }

    public void loadAllTabData() {

        if (mCollectTypes == null || mCollectTypes.length < 1) {
            mView.onShowEmptyView();
            return;
        }

        List tabList = new ArrayList<>();

        Collect collect = new Collect();
        for (int i = 0; i < mCollectTypes.length; i++) {
            collect.setCollectType(mCollectTypes[i]);
            CollectTabInfo collectTabInfo = getCollectTabInfo(collect.getCollectType());
            List<Collect> list = mCollectModel.getData(collect);
            if (list != null && list.size() > 0) {
                tabList.add(collectTabInfo);
            }
        }

        if (tabList.size() > 0) {
            mView.onShowSuccessView(tabList);
        } else {
            mView.onShowEmptyView();
        }
    }

    private CollectTabInfo getCollectTabInfo(String collectType) {
        CollectTabInfo collectTabInfo = new CollectTabInfo();
        collectTabInfo.setTitle(getCollectTitle(collectType));
        collectTabInfo.setCollectType(collectType);
        return collectTabInfo;
    }

    private String getCollectTitle(String collectType) {
        if (TextUtils.equals(collectType, CollectType.SISTER)) {
            return mContext.getString(R.string.fragment_sister);
        } else if (TextUtils.equals(collectType, CollectType.CARICATURE)) {
            return mContext.getString(R.string.fragment_caricature);
        }
        return "";
    }
}
