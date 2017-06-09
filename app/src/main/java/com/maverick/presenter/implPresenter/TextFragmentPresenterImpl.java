package com.maverick.presenter.implPresenter;

import android.content.Context;
import android.util.Log;

import com.maverick.api.ApiManager;
import com.maverick.bean.GifInfo;
import com.maverick.bean.GifInfoObj;
import com.maverick.global.UrlData;
import com.maverick.presenter.TextFragmentPresenter;
import com.maverick.presenter.implView.TextFragmentView;

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
public class TextFragmentPresenterImpl extends BasePresenter implements TextFragmentPresenter {

    private TextFragmentView mTextFragmentView;

    public TextFragmentPresenterImpl(Context context, TextFragmentView view) {
        this.mTextFragmentView = view;
    }

    @Override
    public void getImgList(int page, final boolean clean) {
        mTextFragmentView.showLoading();
        ApiManager.newInstance().getImgApi().getTextList(UrlData.APPID_VALUE, UrlData.SIGN_VALUE, page + "", "20")
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
                            mTextFragmentView.showError();
                            mTextFragmentView.hideLoading();
                            return;
                        }

                        mTextFragmentView.refreshAdapter(gifInfoObj.showapi_res_body.contentlist, clean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        Log.e("lmf", e.getMessage());
                        mTextFragmentView.showError();
                        mTextFragmentView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        mTextFragmentView.hideLoading();
                        Log.e("lmf", "onComplete");
                    }
                });
    }
}
