package com.maverick.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.base.BaseFragment;
import com.maverick.bean.BigImgInfo;
import com.maverick.presenter.BasePresenter;
import com.maverick.util.GlideUtil;

/**
 * Created by limingfei on 2018/2/28.
 */

public class ImageFragment extends BaseFragment {

    private ImageView mImageView;
    private String mImgUrl;
    private BigImgInfo mBigImgInfo;

    public static ImageFragment newInstance(BigImgInfo info, String imgUrl) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", info);
        bundle.putString("imgUrl", imgUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_image;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }


    @Override
    protected void onInitView(View view) {
        mImageView = view.findViewById(R.id.iv_image);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        if (getArguments() == null) {
            return;
        }

        mBigImgInfo = (BigImgInfo) getArguments().getSerializable("info");
        mImgUrl = getArguments().getString("imgUrl");

        if (mBigImgInfo == null) {
            return;
        }

        if (!TextUtils.isEmpty(mImgUrl) && TextUtils.equals(mBigImgInfo.getImg(), mImgUrl)) {
            ViewCompat.setTransitionName(mImageView, DetailActivity.EXTRA_IMAGE);
        }

        GlideUtil.loadImage(getContext(), mBigImgInfo.getImg(), mImageView);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mImageView != null && mBigImgInfo != null && !TextUtils.isEmpty(mImgUrl) && TextUtils.equals(mBigImgInfo.getImg(), mImgUrl)) {
            ViewCompat.setTransitionName(mImageView, isVisibleToUser ? DetailActivity.EXTRA_IMAGE : "");
        }
    }
}
