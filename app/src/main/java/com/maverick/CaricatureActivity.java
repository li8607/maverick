package com.maverick;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.maverick.adapter.CaricatureActivityAdapter;
import com.maverick.base.BaseActivity;
import com.maverick.bean.CaricatureDetailInfo;
import com.maverick.bean.CaricatureListInfo;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.CaricatureActivityPresenter;
import com.maverick.presenter.implView.ICaricatureActivityView;
import com.maverick.transformer.DepthPageTransformer;
import com.maverick.transformer.MyTransformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limingfei on 2017/10/23.
 */
public class CaricatureActivity extends BaseActivity implements ICaricatureActivityView {

    public static final String EXTRA_IMAGE = "Activity";
    private CaricatureActivityPresenter mPresenter;
    private ViewPager viewpager;
    private List<View> mList = new ArrayList<>();

    public static void launch(Context context, CaricatureDetailInfo info) {

        if (context == null || info == null || TextUtils.isEmpty(info.getId())) {
            return;
        }

        Intent intent = new Intent(context, CaricatureActivity.class);
        intent.putExtra(EXTRA_IMAGE, info);
        context.startActivity(intent);
    }


    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new CaricatureActivityPresenter(this, this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_caricature;
    }

    @Override
    protected void onInitView() {
        viewpager = findView(R.id.viewpager);
//        viewpager.setPageTransformer(true, new DepthPageTransformer());
        viewpager.setPageTransformer(true, new MyTransformation());
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        Intent intent = getIntent();
        CaricatureDetailInfo mInfo = (CaricatureDetailInfo) intent.getSerializableExtra(EXTRA_IMAGE);
        mPresenter.refreshData(mInfo);
    }

    @Override
    public void onShowSuccessView(CaricatureListInfo info) {
//        for (String url : info.getImgList()) {
//            ImageView imageView = new ImageView(this);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            GlideUtil.loadImage(this, url, imageView);
//            mList.add(imageView);
//        }

        CaricatureActivityAdapter mAdapter = new CaricatureActivityAdapter(info.getImgList());
        viewpager.setAdapter(mAdapter);
    }

    @Override
    public void onShowEmptyView() {

    }

    @Override
    public void onShowErrorView() {

    }
}
