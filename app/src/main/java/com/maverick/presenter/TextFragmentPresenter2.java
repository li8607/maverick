package com.maverick.presenter;

import android.content.Context;

import com.maverick.bean.GifInfoObj;
import com.maverick.imodel.IJokeModel;
import com.maverick.model.JokeModel;
import com.maverick.presenter.implView.TextFragmentView;

/**
 * Created by ll on 2017/5/22.
 */
public class TextFragmentPresenter2 extends BasePresenter {

    private TextFragmentView mView;
    private final IJokeModel mModel;
    private int mPage = 1;
    private int mNum = 20;

    public TextFragmentPresenter2(Context context, TextFragmentView view) {
        this.mView = view;
        this.mModel = new JokeModel();
    }

    public void refreshData() {

        mModel.requestTextData(1, mNum, new IJokeModel.OnResultListener() {
            @Override
            public void onSuccess(GifInfoObj infoObj) {

                if (infoObj.showapi_res_body.contentlist != null && infoObj.showapi_res_body.contentlist.size() > 1) {
                    mView.onShowSuccessView(infoObj.showapi_res_body.contentlist);
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

    @Override
    public void release() {
        if (mModel != null) {
            mModel.release();
        }
    }

    public void loadMoreData() {
        mModel.requestTextData(mPage + 1, mNum, new IJokeModel.OnResultListener() {
            @Override
            public void onSuccess(GifInfoObj infoObj) {
                mView.onLoadMoreSuccess(infoObj.showapi_res_body.contentlist, true);
                mPage++;
            }

            @Override
            public void onFail() {
                mView.onLoadMoreFail();
            }
        });
    }
}
