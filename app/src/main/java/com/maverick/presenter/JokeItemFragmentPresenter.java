package com.maverick.presenter;

import android.content.Context;

import com.maverick.bean.GifInfo;
import com.maverick.bean.GifInfoObj;
import com.maverick.bean.JokeTabInfo;
import com.maverick.global.Tag;
import com.maverick.hepler.BeanHelper;
import com.maverick.imodel.IJokeModel;
import com.maverick.model.CollectModel;
import com.maverick.model.JokeModel;
import com.maverick.presenter.implView.IJokeItemFragmentView;

import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by ll on 2017/5/22.
 */
public class JokeItemFragmentPresenter extends BasePresenter {

    private String TAG = getClass().getSimpleName();

    private IJokeItemFragmentView mView;
    private final IJokeModel mModel;
    private int mPage = 1;
    private int mNum = 20;

    private JokeTabInfo mJokeTabInfo;

    private List<GifInfo> mList;

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

    public void success(List<GifInfo> list) {
        if (list != null && list.size() > 1) {
            checkCollect(list);
            mView.onShowSuccessView(list, list.size() >= mNum);
        } else {
            mView.onShowEmptyView();
        }
    }

    public void successLoadMore(List<GifInfo> list) {
        int positionStart = 0;
        checkCollect(list);
        if (mList != null) {
            positionStart = mList.size();
            mList.addAll(list);
        } else {
            mList = list;
        }
        mView.onLoadMoreSuccess(mList, positionStart, list.size(), list.size() >= mNum);
    }

    private void refreshGifData() {
        mModel.requestGifData(1, mNum, new IJokeModel.OnGifResultListener() {
            @Override
            public void onSuccess(GifInfoObj infoObj) {
                mList = infoObj.showapi_res_body.contentlist;
                JokeItemFragmentPresenter.this.success(mList);
                mPage = 1;
            }

            @Override
            public void onFail() {
                mView.onShowErrorView();
            }
        });
    }

    public void checkCollect(List<GifInfo> list) {

        if (list == null || list.size() < 1) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            GifInfo gifInfo = list.get(i);
            Collect collect = BeanHelper.getCollect(gifInfo);
            boolean isCollect = CollectModel.newInstance().hasCollectDB(collect);
            gifInfo.setCollect(isCollect);
        }

    }

    private void refreshImgData() {
        mModel.requestImgData(1, mNum, new IJokeModel.OnImgResultListener() {
            @Override
            public void onSuccess(GifInfoObj infoObj) {
                mList = infoObj.showapi_res_body.contentlist;
                JokeItemFragmentPresenter.this.success(mList);
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
                mList = infoObj.showapi_res_body.contentlist;
                JokeItemFragmentPresenter.this.success(mList);
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
                successLoadMore(infoObj.showapi_res_body.contentlist);
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
                successLoadMore(infoObj.showapi_res_body.contentlist);
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
                successLoadMore(infoObj.showapi_res_body.contentlist);
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

    public void setPage(int page) {
        mPage = page;
    }

    public void setJokeTabInfo(JokeTabInfo info) {
        this.mJokeTabInfo = info;
    }

    public List<GifInfo> getData() {
        return mList;
    }

    public void setData(List<GifInfo> mList) {
        this.mList = mList;
    }
}
