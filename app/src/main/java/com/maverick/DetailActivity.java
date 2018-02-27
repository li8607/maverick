package com.maverick;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.maverick.base.BaseActivity;
import com.maverick.bean.BigImgInfo;
import com.maverick.bean.MenuDetailInfo;
import com.maverick.dialog.MenuDialog;
import com.maverick.dialog.MultifunctionalDialog;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.DetailActivityPresenter;
import com.maverick.presenter.implView.IDetailActivityView;
import com.maverick.type.ShareType;
import com.maverick.util.GlideUtil;
import com.umeng.socialize.UMShareAPI;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ll on 2017/5/25.
 */
public class DetailActivity extends BaseActivity implements IDetailActivityView, View.OnLongClickListener {
    public static final String EXTRA_IMAGE = "DetailActivity:image";
    public static final String EXTRA_IMAGE2 = "DetailActivity:image";
    private DetailActivityPresenter mPresenter;
    private ImageView image_detail;
    private SubsamplingScaleImageView mSubsamplingScaleImageView;
    private BigImgInfo mBigImgInfo;
    private ImageView imageView2;

    public static void launch(Activity activity, View transitionView, BigImgInfo info) {

        if (info == null || TextUtils.isEmpty(info.getImg())) {
            return;
        }

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, EXTRA_IMAGE);

        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, info);

        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
    }

    public static void launch(Activity activity, View transitionView, List<BigImgInfo> bigImgInfos, int position) {

        if (bigImgInfos == null || bigImgInfos.size() < 1) {
            return;
        }

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, EXTRA_IMAGE);

        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_IMAGE2, (Serializable) bigImgInfos);
        intent.putExtra(EXTRA_IMAGE, bigImgInfos.get(position));

        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
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
        image_detail = (ImageView) findViewById(R.id.image_detail);
        mSubsamplingScaleImageView = (SubsamplingScaleImageView) findViewById(R.id.imageView);
        mSubsamplingScaleImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        mSubsamplingScaleImageView.setMinScale(1.0F);

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBarBackground));
//            window.setNavigationBarColor(getResources().getColor(R.color.colorStatusBarBackground));
            getWindow().setStatusBarColor(Color.TRANSPARENT);// Color.TRANSPARENT = 0 表示#00000000即透明颜色
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        image_detail.setOnLongClickListener(this);
        mSubsamplingScaleImageView.setOnLongClickListener(this);

        imageView2 = findView(R.id.imageView2);
    }

    private void showMultifunctionalDialog() {
        if (mBigImgInfo != null) {
            MultifunctionalDialog mMultifunctionalDialog = MultifunctionalDialog.newInstance(mBigImgInfo.getImg());
            showDialogFragment(mMultifunctionalDialog);
        }
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mBigImgInfo = (BigImgInfo) intent.getSerializableExtra(EXTRA_IMAGE);
        if (mBigImgInfo == null) {
            return;
        }
        mPresenter.loadImage(mBigImgInfo.getImg());
    }

    @Override
    public void onShowGifImageView(String imgUrl) {
        image_detail.setVisibility(View.VISIBLE);
        GlideUtil.loadImage(this, imgUrl, image_detail);
    }

    @Override
    public void onShowImageView(String imgUrl) {
        imageView2.setVisibility(View.VISIBLE);
        ViewCompat.setTransitionName(imageView2, EXTRA_IMAGE);
        GlideUtil.loadImage(this, imgUrl, imageView2);
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

        if (mBigImgInfo == null) {
            return;
        }

        MenuDetailInfo menuDetailInfo = new MenuDetailInfo();
        menuDetailInfo.setShareType(ShareType.IMAGE_TEXT);
        menuDetailInfo.setTitle(mBigImgInfo.getTitle());
        menuDetailInfo.setImageurl(mBigImgInfo.getImg());
        menuDetailInfo.setWeburl(mBigImgInfo.getWebUrl());
        menuDetailInfo.setCollect(mBigImgInfo.getCollect());
        MenuDialog dialog = MenuDialog.newInstance(menuDetailInfo);
        showDialogFragment(dialog);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
