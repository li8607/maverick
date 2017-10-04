package com.maverick.presenter;

import android.content.Context;

import com.maverick.bean.GifInfoObj;
import com.maverick.bean.JokeTabInfo;
import com.maverick.global.Tag;
import com.maverick.imodel.IJokeModel;
import com.maverick.model.JokeModel;
import com.maverick.presenter.implView.IJokeItemFragmentView;

/**
 * Created by ll on 2017/5/22.
 */
public class JokeItemFragmentPresenter extends BasePresenter {

    private IJokeItemFragmentView mView;
    private final IJokeModel mModel;
    private int mPage = 1;
    private int mNum = 20;

    private JokeTabInfo mJokeTabInfo;

    public JokeItemFragmentPresenter(Context context, IJokeItemFragmentView view) {
        this.mView = view;
        this.mModel = new JokeModel();
    }

    public void refreshData(JokeTabInfo jokeTabInfo) {
        this.mJokeTabInfo = jokeTabInfo;
        if (jokeTabInfo == null) {
            mView.onShowErrorView();
            return;
        }

        switch (jokeTabInfo.getType()) {
            case Tag.JOKE_TAB_TEXT:
                refreshTextData();
                break;
            case Tag.JOKE_TAB_IMG:
                refreshImgData();
                break;
            case Tag.JOKE_TAB_GIF:
                refreshGifData();
                break;
            default:
                mView.onShowErrorView();
                break;
        }
    }

    private void refreshGifData() {
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

    private void refreshImgData() {
        mModel.requestImgData(1, mNum, new IJokeModel.OnImgResultListener() {
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

    private void refreshTextData() {
        mModel.requestTextData(1, mNum, new IJokeModel.OnTextResultListener() {
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

        if (mJokeTabInfo == null) {
            mView.onLoadMoreFail();
            return;
        }

        switch (mJokeTabInfo.getType()) {
            case Tag.JOKE_TAB_TEXT:
                loadMoreTextData();
                break;
            case Tag.JOKE_TAB_IMG:
                loadMoreImgData();
                break;
            case Tag.JOKE_TAB_GIF:
                loadMoreGifData();
                break;
            default:
                mView.onLoadMoreFail();
                break;
        }
    }

    private void loadMoreGifData() {
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

    private void loadMoreImgData() {
        mModel.requestImgData(mPage + 1, mNum, new IJokeModel.OnImgResultListener() {
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

    private void loadMoreTextData() {
        mModel.requestTextData(mPage + 1, mNum, new IJokeModel.OnTextResultListener() {
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
