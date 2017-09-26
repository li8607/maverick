package com.maverick.presenter;

import android.content.Context;
import android.util.Log;

import com.android.wonderokhttp.http.HttpUtil;
import com.maverick.api.BeautyApi;
import com.maverick.bean.BeautyInfo;
import com.maverick.bean.BeautyItemInfo;
import com.maverick.imodel.IBeautyModel;
import com.maverick.model.BeautyModel;
import com.maverick.presenter.implView.IBeautyFragmentView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

//    http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1

    public BeautyFragmentPresenter(Context context, IBeautyFragmentView view) {
        this.mContext = context;
        this.mView = view;
        this.mModel = new BeautyModel();
    }

    public void refreshData() {
        mModel.requestData(1, mNum, new IBeautyModel.OnResultListener() {
            @Override
            public void onSuccess(List<BeautyItemInfo> beautyInfos) {
                if (beautyInfos != null && beautyInfos.size() >= 1) {
                    mView.onShowSuccessView(beautyInfos);
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
        mModel.requestData(mPage + 1, mNum, new IBeautyModel.OnResultListener() {
            @Override
            public void onSuccess(List<BeautyItemInfo> beautyInfos) {
                mView.onLoadMoreSuccess(beautyInfos, beautyInfos.size() >= mNum);
                mPage++;
            }

            @Override
            public void onFail() {
                mView.onLoadMoreFail();
            }
        });
    }

}
