package com.maverick.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.maverick.bean.PearVideoInfo;
import com.maverick.bean.PearVideoTabDetailInfo;
import com.maverick.bean.PearVideoTabInfo;
import com.maverick.imodel.IPearModel;
import com.maverick.model.PearModel;
import com.maverick.presenter.implView.IPearItemFragmentView;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public class PearItemFragmentPresenter extends BasePresenter {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private IPearItemFragmentView mView;
    private final IPearModel mModel;
    private PearVideoTabInfo mInfo;
    private PearVideoTabDetailInfo mTabDetailInfo;

    public PearItemFragmentPresenter(Context context, IPearItemFragmentView view) {
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

    public void loadData(PearVideoTabInfo info) {
        this.mInfo = info;
        if (info == null) {
            mView.onShowErrorView();
            return;
        }

        mModel.requestTabItemData(1, info.getCategoryId(), new IPearModel.OnTabItemResultListener() {
            @Override
            public void onSuccess(PearVideoTabDetailInfo info) {
                mTabDetailInfo = info;
                List<PearVideoInfo> hotList = info.getHotList();
                List<PearVideoInfo> list = info.getContList();

                if ((list == null || list.size() < 1) && (hotList == null || hotList.size() < 1)) {
                    mView.onShowEmptyView();
                    return;
                }

                mView.onShowSuccessView(hotList, list, true);
            }

            @Override
            public void onFail() {
                mView.onShowErrorView();
            }
        });
    }

    public void loadMoreData() {
        if (mTabDetailInfo == null || TextUtils.isEmpty(mTabDetailInfo.getNextUrl())) {
            mView.onLoadMoreFail();
            return;
        }

        mModel.requestTabItemNextData(mTabDetailInfo.getNextUrl(), new IPearModel.OnTabItemResultListener() {
            @Override
            public void onSuccess(PearVideoTabDetailInfo info) {
                mTabDetailInfo = info;
                List<PearVideoInfo> list = info.getContList();

                if (list == null || list.size() < 1) {
                    mView.onLoadMoreFail();
                    return;
                }

                mView.onLoadMoreSuccess(list, !TextUtils.isEmpty(info.getNextUrl()));
            }

            @Override
            public void onFail() {
                mView.onLoadMoreFail();
            }
        });
    }
}
