package com.maverick;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.maverick.adapter.CaricatureActivityAdapter;
import com.maverick.base.BaseActivity;
import com.maverick.bean.CaricatureDetailInfo;
import com.maverick.bean.CaricatureListInfo;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.CaricatureActivityPresenter;
import com.maverick.presenter.implView.ICaricatureActivityView;
import com.maverick.transformer.DepthPageTransformer;

/**
 * Created by limingfei on 2017/10/23.
 */
public class CaricatureActivity extends BaseActivity implements ICaricatureActivityView {

    public static final String EXTRA_IMAGE = "Activity";
    private CaricatureActivityPresenter mPresenter;
    private ViewPager viewpager;
    private ViewGroup root;
    private CaricatureDetailInfo mInfo;

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

        root = findView(R.id.root);

        viewpager = findView(R.id.viewpager);
        viewpager.setPageTransformer(true, new DepthPageTransformer());
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        Intent intent = getIntent();
        mInfo = (CaricatureDetailInfo) intent.getSerializableExtra(EXTRA_IMAGE);
        mPresenter.refreshData(mInfo);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                View decorView = getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
            }
        }
    }

    @Override
    public void onShowSuccessView(CaricatureListInfo info) {
        CaricatureActivityAdapter mAdapter = new CaricatureActivityAdapter(info.getImgList());
        viewpager.setAdapter(mAdapter);
    }

    @Override
    public void onShowEmptyView() {
        View view = View.inflate(root.getContext(), R.layout.view_empty, null);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        root.addView(view, layoutParams);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.removeView(v);
                mPresenter.refreshData(mInfo);
            }
        });
    }

    @Override
    public void onShowErrorView() {
        View view = View.inflate(root.getContext(), R.layout.view_error, null);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;

        root.addView(view, layoutParams);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.removeView(v);
                mPresenter.refreshData(mInfo);
            }
        });
    }
}
