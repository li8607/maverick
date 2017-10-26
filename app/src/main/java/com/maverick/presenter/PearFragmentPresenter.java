package com.maverick.presenter;

import android.content.Context;

import com.maverick.bean.PearVideoTabInfo;
import com.maverick.imodel.IPearModel;
import com.maverick.model.PearModel;
import com.maverick.presenter.implView.IPearFragmentView;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public class PearFragmentPresenter extends BasePresenter {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private IPearFragmentView mView;
    private IPearModel mModel;

    public PearFragmentPresenter(Context context, IPearFragmentView view) {
        this.mContext = context;
        this.mView = view;
        this.mModel = new PearModel();
    }

    @Override
    public void release() {
        if (mModel != null) {
            mModel.release();
        }
    }

    public void loadData() {

        mModel.requestData(new IPearModel.OnResultListener() {
            @Override
            public void onSuccess(List<PearVideoTabInfo> list) {
                if (list == null || list.size() < 1) {
                    mView.onShowEmptyView();
                    return;
                }
                mView.onShowSuccessView(list);
            }

            @Override
            public void onFail() {
                mView.onShowErrorView();
            }
        });
    }
}
