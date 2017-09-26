package com.maverick.presenter;

import android.content.Context;

import com.android.wonderokhttp.http.HttpUtil;
import com.maverick.bean.BeautyItemInfo;
import com.maverick.imodel.IBeautyModel;
import com.maverick.model.BeautyModel;
import com.maverick.presenter.implView.IBeautyFragmentView;

import java.util.List;

/**
 * Created by limingfei on 2017/9/26.
 */
public class BeautyFragmentPresenter extends BasePresenter {
    private IBeautyModel mModel;
    private Context mContext;
    private IBeautyFragmentView mView;
    private int mPage = 1;
    private int mNum = 20;

//    http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1

    public BeautyFragmentPresenter(Context context, IBeautyFragmentView view) {
        this.mContext = context;
        this.mView = view;
        this.mModel = new BeautyModel();
    }

    public void refreshData() {
        mModel.requestData(1, mNum, new IBeautyModel.OnResultListener() {
            @Override
            public void onSuccess(List<BeautyItemInfo> beautyInfo) {
                if(beautyInfo != null && beautyInfo.size() >= 1) {
                    mView.onShowSuccessView(beautyInfo);
                }else {
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

}
