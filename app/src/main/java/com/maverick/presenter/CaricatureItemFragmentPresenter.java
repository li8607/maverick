package com.maverick.presenter;

import android.content.Context;
import android.util.Log;

import com.maverick.bean.CaricatureInfo;
import com.maverick.bean.CaricatureTabInfo;
import com.maverick.imodel.ICaricatureModel;
import com.maverick.model.CaricatureModel;
import com.maverick.presenter.implView.ICaricatureItemFragmentView;

import java.util.List;

/**
 * Created by limingfei on 2017/10/20.
 */
public class CaricatureItemFragmentPresenter extends BasePresenter {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private ICaricatureItemFragmentView mView;
    private final ICaricatureModel mModel;
    private int mPage = 1;
    private CaricatureTabInfo mInfo;

    public CaricatureItemFragmentPresenter(Context context, ICaricatureItemFragmentView view) {
        this.mContext = context;
        this.mView = view;
        this.mModel = new CaricatureModel();
    }

    @Override
    public void release() {
        if (mModel != null) {
            mModel.release();
        }
    }

    public void refreshData(CaricatureTabInfo info) {
        this.mInfo = info;
        if (info == null) {
            mView.onShowErrorView();
            return;
        }
        mPage = 1;
        mModel.requestData(info.getType(), mPage, new ICaricatureModel.OnResultListener() {
            @Override
            public void onSuccess(List<CaricatureInfo> list, boolean isHasMore) {
                if (list != null && list.size() > 0) {
                    mView.onShowSuccessView(list, isHasMore);
                } else {
                    mView.onShowEmptyView();
                }

                mPage++;
            }

            @Override
            public void onFail() {
                mView.onShowErrorView();
            }
        });
    }

    public void loadMoreData() {

        if (mInfo == null) {
            mView.onLoadMoreFail();
            return;
        }

        mModel.requestData(mInfo.getType(), mPage, new ICaricatureModel.OnResultListener() {
            @Override
            public void onSuccess(List<CaricatureInfo> list, boolean isHasMore) {
                mView.onLoadMoreSuccess(list, isHasMore);
                mPage++;
            }

            @Override
            public void onFail() {
                mView.onLoadMoreFail();
            }
        });
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }
}
