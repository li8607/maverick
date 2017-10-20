package com.maverick.presenter;

import android.content.Context;

import com.maverick.bean.SinaInfo;
import com.maverick.bean.SinaTabInfo;
import com.maverick.imodel.ISinaModel;
import com.maverick.model.SinaModel;
import com.maverick.presenter.implView.ISinaItemFragmentView;

import java.util.List;

/**
 * Created by limingfei on 2017/10/20.
 */
public class SinaItemFragmentPresenter extends BasePresenter {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private ISinaItemFragmentView mView;
    private final ISinaModel mModel;
    private int mPage = 1;
    private SinaTabInfo mInfo;

    public SinaItemFragmentPresenter(Context context, ISinaItemFragmentView view) {
        this.mContext = context;
        this.mView = view;
        this.mModel = new SinaModel();
    }

    @Override
    public void release() {
        if (mModel != null) {
            mModel.release();
        }
    }

    public void refreshData(SinaTabInfo info) {
        this.mInfo = info;
        if (info == null) {
            mView.onShowErrorView();
            return;
        }
        mPage = 1;
        mModel.requestData(info.getType(), info.getSpace(), mPage, new ISinaModel.OnResultListener() {
            @Override
            public void onSuccess(List<SinaInfo> list, boolean isHasMore) {
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

        mModel.requestData(mInfo.getType(), mInfo.getSpace(), mPage, new ISinaModel.OnResultListener() {
            @Override
            public void onSuccess(List<SinaInfo> list, boolean isHasMore) {
                mView.onLoadMoreSuccess(list, isHasMore);
                mPage++;
            }

            @Override
            public void onFail() {
                mView.onShowErrorView();
            }
        });
    }
}
