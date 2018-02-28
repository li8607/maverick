package com.maverick;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.maverick.adapter.DetailActivityAdapter;
import com.maverick.base.BaseActivity;
import com.maverick.bean.BigImgInfo;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.DetailActivityPresenter;
import com.maverick.presenter.implView.IDetailActivityView;
import com.umeng.socialize.UMShareAPI;

import java.io.Serializable;
import java.util.List;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

/**
 * Created by ll on 2017/5/25.
 */
public class DetailActivity extends BaseActivity implements IDetailActivityView, View.OnLongClickListener {
    public static final String EXTRA_IMAGE = "DetailActivity:image";
    public static final String EXTRA_IMAGE2 = "DetailActivity:image2";
    private DetailActivityPresenter mPresenter;
    private SubsamplingScaleImageView mSubsamplingScaleImageView;
    private ViewPager mViewPager;
    private List<BigImgInfo> mBigImgInfoList;
    private BigImgInfo mBigImgInfo;

    public static void launch(Activity activity, View transitionView, BigImgInfo info) {

        if (info == null || TextUtils.isEmpty(info.getImg())) {
            return;
        }

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, EXTRA_IMAGE);

        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, info);

        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
    }

    public static void launch(Activity activity, View transitionView, List<BigImgInfo> bigImgInfos, BigImgInfo bigImgInfo) {

        if (bigImgInfos == null || bigImgInfos.size() < 1) {
            return;
        }

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, activity.getString(R.string.image_transition_name));

        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_IMAGE2, (Serializable) bigImgInfos);
        intent.putExtra(EXTRA_IMAGE, bigImgInfo);

        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
    }

    public static void launch(Activity activity, View transitionView, List<BigImgInfo> bigImgInfos) {
        launch(activity, transitionView, bigImgInfos, null);
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new DetailActivityPresenter(this, this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onInitView() {

        mViewPager = findView(R.id.vp_detail_image);

        mSubsamplingScaleImageView = (SubsamplingScaleImageView) findViewById(R.id.imageView);
        mSubsamplingScaleImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        mSubsamplingScaleImageView.setMinScale(1.0F);

        mSubsamplingScaleImageView.setOnLongClickListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (TextUtils.equals(mBigImgInfoList.get(position).getImg(), mBigImgInfo.getImg())) {
                    ViewCompat.setTransitionName(mViewPager, getString(R.string.image_transition_name));
                } else {
                    ViewCompat.setTransitionName(mViewPager, "");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        getWindow().addFlags(FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
    }

    private void showMultifunctionalDialog() {
//        if (mBigImgInfo != null) {
//            MultifunctionalDialog mMultifunctionalDialog = MultifunctionalDialog.newInstance(mBigImgInfo.getImg());
//            showDialogFragment(mMultifunctionalDialog);
//        }
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        Intent intent = getIntent();

        if (intent == null) {
            return;
        }

        mBigImgInfoList = (List<BigImgInfo>) intent.getSerializableExtra(EXTRA_IMAGE2);
        mBigImgInfo = (BigImgInfo) intent.getSerializableExtra(EXTRA_IMAGE);

        DetailActivityAdapter mAdapter = new DetailActivityAdapter(getSupportFragmentManager());
        mAdapter.setList(mBigImgInfoList);
        mViewPager.setAdapter(mAdapter);

        if (mBigImgInfo == null || mBigImgInfoList == null || mBigImgInfoList.size() < 1) {
            return;
        }

        for (int i = 0; i < mBigImgInfoList.size(); i++) {
            if (TextUtils.equals(mBigImgInfoList.get(i).getImg(), mBigImgInfo.getImg())) {
                ViewCompat.setTransitionName(mViewPager, getString(R.string.image_transition_name));
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }

    @Override
    public void onShowGifImageView(String imgUrl) {

    }

    @Override
    public void onShowImageView(String imgUrl) {
//        imageView2.setVisibility(View.VISIBLE);
//        ViewCompat.setTransitionName(imageView2, EXTRA_IMAGE);
//        GlideUtil.loadImage(this, imgUrl, imageView2);
//        //下载图片保存到本地
//        Glide.with(this)
//                .load(imgUrl).downloadOnly(new SimpleTarget<File>() {
//            @Override
//            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
//                // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
//                mSubsamplingScaleImageView.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(2.0F, new PointF(0, 0), 0));
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        mSubsamplingScaleImageView.recycle();
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    public boolean onLongClick(View v) {
//        showMultifunctionalDialog();
        showShareDialog();
        return true;
    }

    private void showShareDialog() {

//        if (mBigImgInfo == null) {
//            return;
//        }
//
//        MenuDetailInfo menuDetailInfo = new MenuDetailInfo();
//        menuDetailInfo.setShareType(ShareType.IMAGE_TEXT);
//        menuDetailInfo.setTitle(mBigImgInfo.getTitle());
//        menuDetailInfo.setImageurl(mBigImgInfo.getImg());
//        menuDetailInfo.setWeburl(mBigImgInfo.getWebUrl());
//        menuDetailInfo.setCollect(mBigImgInfo.getCollect());
//        MenuDialog dialog = MenuDialog.newInstance(menuDetailInfo);
//        showDialogFragment(dialog);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
