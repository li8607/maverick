package com.maverick.presenter.implPresenter;

import android.content.Context;
import android.util.Log;

import com.maverick.api.ApiManager;
import com.maverick.bean.GifInfo;
import com.maverick.bean.GifInfoObj;
import com.maverick.global.UrlData;
import com.maverick.presenter.ImgFragmentPresenter;
import com.maverick.presenter.implView.ImgFragmentView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ll on 2017/5/22.
 */
public class ImgFragmentPresenterImpl extends BasePresenter implements ImgFragmentPresenter {

    private ImgFragmentView mImgFragmentView;

    private List<GifInfo> mList = new ArrayList<>();

    public ImgFragmentPresenterImpl(Context context, ImgFragmentView view) {
        this.mImgFragmentView = view;
    }

    @Override
    public void getImgList(int page, final boolean clean) {
        mImgFragmentView.showLoading();
        ApiManager.newInstance().getImgApi().getImgList(UrlData.APPID_VALUE, UrlData.SIGN_VALUE, page + "", "20")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GifInfoObj>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("lmf", "onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull GifInfoObj gifInfoObj) {
                        Log.e("lmf", "onNext");

                        if (gifInfoObj == null || gifInfoObj.showapi_res_body == null || gifInfoObj.showapi_res_body.contentlist == null) {
                            mImgFragmentView.showError();
                            mImgFragmentView.hideLoading();
                            return;
                        }

                        if (clean) {
                            mList.clear();
                        }

                        mList.addAll(gifInfoObj.showapi_res_body.contentlist);
                        mImgFragmentView.refreshAdapter(mList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        Log.e("lmf", e.getMessage());
                        mImgFragmentView.showError();
                        mImgFragmentView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        mImgFragmentView.hideLoading();
                        Log.e("lmf", "onComplete");
                    }
                });
    }
}
