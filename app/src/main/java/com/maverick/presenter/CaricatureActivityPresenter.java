package com.maverick.presenter;

import android.content.Context;

import com.maverick.bean.CaricatureDetailInfo;
import com.maverick.bean.CaricatureListInfo;
import com.maverick.imodel.ICaricatureModel;
import com.maverick.model.CaricatureModel;
import com.maverick.presenter.implView.ICaricatureActivityView;

/**
 * Created by limingfei on 2017/10/23.
 */
public class CaricatureActivityPresenter extends BasePresenter {

    private Context mContext;
    private ICaricatureActivityView mView;
    private ICaricatureModel mModel;

    public CaricatureActivityPresenter(Context context, ICaricatureActivityView view) {
        this.mContext = context;
        this.mView = view;
        this.mModel = new CaricatureModel();
    }

    @Override
    public void release() {

    }

    public void refreshData(CaricatureDetailInfo info) {
        if (info == null) {
            mView.onShowErrorView();
            return;
        }

        mModel.requestDetailData(info.getId(), new ICaricatureModel.OnDetailResultListener() {
            @Override
            public void onSuccess(CaricatureListInfo info) {
                mView.onShowSuccessView(info);
            }

            @Override
            public void onFail() {
                mView.onShowErrorView();
            }
        });
    }
}
