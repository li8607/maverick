package com.maverick.presenter;

import android.content.Context;
import android.util.Log;

import com.maverick.bean.SisterDetailInfo;
import com.maverick.bean.SisterInfo;
import com.maverick.imodel.ISisterFragmentModel;
import com.maverick.model.SisterFragmentModel;
import com.maverick.presenter.implView.ISisterFragmentView;

import java.util.List;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterFragmentPresenter extends BasePresenter {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private ISisterFragmentView mView;
    private final ISisterFragmentModel mModel;
    private int mPage = 1;
    private SisterDetailInfo mSisterDetailInfo;

    public SisterFragmentPresenter(Context context, ISisterFragmentView view) {
        this.mContext = context;
        this.mView = view;
        this.mModel = new SisterFragmentModel();
    }

    @Override
    public void release() {
        if (mModel != null) {
            mModel.release();
        }
    }

    public void refreshData(SisterDetailInfo sisterDetailInfo) {
        mSisterDetailInfo = sisterDetailInfo;

        if (sisterDetailInfo == null) {
            mView.onShowErrorView();
            return;
        }

        mModel.requestData(sisterDetailInfo.getType(), sisterDetailInfo.getTitle(), 1, new ISisterFragmentModel.OnResultListener() {

            @Override
            public void onSuccess(List<SisterInfo> list) {
                if (list != null && list.size() >= 1) {

                    for (int i = 0; i < list.size(); i++) {
                        Log.e(TAG, "" + list.get(i).getImage2());
                        SisterInfo sisterInfo = list.get(i);
                        sisterInfo.setComment(getRandom(sisterInfo.getLove()) + "");
                        sisterInfo.setShare(getRandom(sisterInfo.getLove()) + "");
                    }

                    mView.onShowSuccessView(list);
                } else {
                    mView.onShowEmptyView();
                }
                mPage = 1;
            }

            @Override
            public void onFail() {
                mView.onShowErrorView();
            }
        });
    }

    public void loadMoreData() {

        if (mSisterDetailInfo == null) {
            mView.onLoadMoreFail();
            return;
        }

        mModel.requestData(mSisterDetailInfo.getType(), mSisterDetailInfo.getTitle(), mPage + 1, new ISisterFragmentModel.OnResultListener() {

            @Override
            public void onSuccess(List<SisterInfo> list) {
                //此接口固定20条数据
                mView.onLoadMoreSuccess(list, list.size() >= 20);
                mPage++;
            }

            @Override
            public void onFail() {
                mView.onLoadMoreFail();
            }
        });
    }

    public int getRandom(String ding) {
        int dingCount = getString2Int(ding);
        return (int) (Math.random() * dingCount);
    }

    public int getString2Int(String str) {

        try {
            return Integer.parseInt(str);
        } catch (Exception e) {

        }
        return 0;
    }
}
