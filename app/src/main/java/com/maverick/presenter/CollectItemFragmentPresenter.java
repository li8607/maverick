package com.maverick.presenter;

import android.content.Context;
import android.util.Log;

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

        List<Collect> list;
        switch (collectTabInfo.getType()) {
            case 3:
                list = CollectModel.newInstance().getJokeTextData();
                break;
            case 4:
                list = CollectModel.newInstance().getJokeImgData();
                break;
            case 5:
                list = CollectModel.newInstance().getJokeGifData();
                break;
            case 6:
                list = CollectModel.newInstance().getSisterData();
                break;
            case 7:
                list = CollectModel.newInstance().getCaricatureData();
                break;
            default:
                list = null;
                break;
        }

        Log.e(TAG, "list.size() = " + list.size());

        if (list != null && list.size() > 0) {
            mView.onShowSuccessView(list);
        } else {
            mView.onShowEmptyView();
        }
    }
}
