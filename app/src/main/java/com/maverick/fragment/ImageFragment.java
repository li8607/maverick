package com.maverick.fragment;

import android.os.Bundle;
import android.view.View;

import com.maverick.R;
import com.maverick.base.BaseFragment;
import com.maverick.bean.BigImgInfo;
import com.maverick.presenter.BasePresenter;
import com.maverick.util.GlideUtil;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by limingfei on 2018/2/28.
 */

public class ImageFragment extends BaseFragment {

    private PhotoView mImageView;

    public static ImageFragment newInstance(BigImgInfo info) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", info);
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

        BigImgInfo mBigImgInfo = (BigImgInfo) getArguments().getSerializable("info");

        if (mBigImgInfo == null) {
            return;
        }

        GlideUtil.loadImage(getContext(), mBigImgInfo.getImg(), mImageView);
    }
}
