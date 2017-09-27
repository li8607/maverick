package com.maverick.presenter;

import android.content.Context;

import com.maverick.bean.GifInfoObj;
import com.maverick.imodel.IJokeModel;
import com.maverick.model.JokeModel;
import com.maverick.presenter.implView.GifFragmentView;

/**
 * Created by limingfei on 2017/9/27.
 */
public class GifFragmentPresenter extends BasePresenter {

    private Context mContext;
    private GifFragmentView mView;
    private final IJokeModel mModel;
    private int mPage = 1;
    private int mNum = 20;

    public GifFragmentPresenter(Context context, GifFragmentView gifFragmentView) {
        this.mContext = context;
        this.mView = gifFragmentView;
        this.mModel = new JokeModel();
    }

    public void refreshData() {

        mModel.requestGifData(1, mNum, new IJokeModel.OnGifResultListener() {
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

    public void loadMoreData() {
        mModel.requestGifData(mPage + 1, mNum, new IJokeModel.OnGifResultListener() {
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


    @Override
    public void release() {
        if(mModel != null) {
            mModel.release();
        }
    }
}
