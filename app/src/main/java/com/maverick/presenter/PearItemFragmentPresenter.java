package com.maverick.presenter;

import android.content.Context;
import android.util.Log;

import com.maverick.bean.PearVideoTabDetailInfo;
import com.maverick.bean.PearVideoTabInfo;
import com.maverick.imodel.IPearModel;
import com.maverick.model.PearModel;
import com.maverick.presenter.implView.IPearItemFragmentView;

/**
 * Created by Administrator on 2017/10/26.
 */

public class PearItemFragmentPresenter extends BasePresenter {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private IPearItemFragmentView mView;
    private final IPearModel mModel;

    public PearItemFragmentPresenter(Context context, IPearItemFragmentView view) {
        this.mContext = context;
        this.mView = view;
        this.mModel = new PearModel();
    }

    @Override
    public void release() {

    }

    public void loadData(PearVideoTabInfo info) {
        mModel.requestTabItemData(1, info.getCategoryId(), new IPearModel.OnTabItemResultListener() {
            @Override
            public void onSuccess(PearVideoTabDetailInfo info) {
                Log.e(TAG, "onSuccess");
            }

            @Override
            public void onFail() {
                Log.e(TAG, "onFail");
            }
        });
    }
}
