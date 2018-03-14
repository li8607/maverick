package com.maverick.presenter;

import android.content.Context;

import com.maverick.bean.BeautyItemInfo;
import com.maverick.hepler.BeanHelper;
import com.maverick.imodel.IBeautyModel;
import com.maverick.model.BeautyModel;
import com.maverick.model.CollectModel;
import com.maverick.presenter.implView.IBeautyFragmentView;

import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/9/26.
 */
public class BeautyFragmentPresenter extends BasePresenter {
    private IBeautyModel mModel;
    private Context mContext;
    private IBeautyFragmentView mView;
    private int mPage = 1;
    private int mNum = 20;
    private String TAG = getClass().getSimpleName();
    private List<BeautyItemInfo> mBeautyInfos;

    public BeautyFragmentPresenter(Context context, IBeautyFragmentView view) {
        this.mContext = context;
        this.mView = view;
        this.mModel = new BeautyModel();
    }

    public void refreshData() {
        mModel.requestData(1, mNum, new IBeautyModel.OnResultListener() {
            @Override
            public void onSuccess(List<BeautyItemInfo> beautyInfos) {
                mBeautyInfos = beautyInfos;
                if (beautyInfos != null && beautyInfos.size() >= 1) {
                    checkCollect(beautyInfos);
                    mView.onShowSuccessView(beautyInfos, beautyInfos.size() >= mNum);
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

    public void checkCollect(List<BeautyItemInfo> beautyInfos) {


        if (beautyInfos == null || beautyInfos.size() < 1) {
            return;
        }

        for (int i = 0; i < beautyInfos.size(); i++) {

            BeautyItemInfo beautyItemInfo = beautyInfos.get(i);
//            History history = new History();
//            history.setHistoryimage(beautyInfos.get(i).getUrl());
//            history.setHistoryName(beautyInfos.get(i).getWho());
//            history.setHistoryType("2");
//            history.setHistoryTime(System.currentTimeMillis());
//            HistoryModel.newInstance().insertHistoryDB(history);
            Collect collect = BeanHelper.getCollect(beautyItemInfo);
            beautyItemInfo.setCheck(CollectModel.newInstance().hasCollectDB(collect));
        }
    }

    public void loadMoreData() {
        mModel.requestData(mPage + 1, mNum, new IBeautyModel.OnResultListener() {
            @Override
            public void onSuccess(List<BeautyItemInfo> beautyInfos) {
                int positionStart = 0;

                if (mBeautyInfos != null) {
                    positionStart = mBeautyInfos.size();
                    mBeautyInfos.addAll(beautyInfos);
                } else {
                    mBeautyInfos = beautyInfos;
                }

                mView.onLoadMoreSuccess(beautyInfos, positionStart, beautyInfos.size(), beautyInfos.size() >= mNum);
                mPage++;
            }

            @Override
            public void onFail() {
                mView.onLoadMoreFail();
            }
        });
    }

    @Override
    public void release() {
        mModel.release();
    }

    public List<BeautyItemInfo> getBeautyInfos() {
        return mBeautyInfos;
    }

    public void setBeautyInfos(List<BeautyItemInfo> beautyInfos) {
        mBeautyInfos = beautyInfos;
    }
}
