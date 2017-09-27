package com.maverick;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.maverick.bean.BigImgInfo;
import com.maverick.bean.GifInfo;
import com.maverick.dialog.MultifunctionalDialog;
import com.maverick.util.GlideUtil;

/**
 * Created by ll on 2017/5/25.
 */
public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE = "DetailActivity:image";
    private String mImgUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final ImageView image_detail = (ImageView) findViewById(R.id.image_detail);
        ViewCompat.setTransitionName(image_detail, EXTRA_IMAGE);
        Intent intent = getIntent();
        final BigImgInfo gifInfo = (BigImgInfo) intent.getSerializableExtra(EXTRA_IMAGE);

        mImgUrl = gifInfo.getImg();

        GlideUtil.loadImage(this, mImgUrl, image_detail, new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        });
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

        image_detail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showMultifunctionalDialog();
                return true;
            }
        });
    }

    private void showMultifunctionalDialog() {
        MultifunctionalDialog mMultifunctionalDialog = MultifunctionalDialog.newInstance(mImgUrl);
        mMultifunctionalDialog.show(getFragmentManager(), "MultifunctionalDialog");
    }

    public static void launch(Activity activity, View transitionView, BigImgInfo info) {

        if (info == null || TextUtils.isEmpty(info.getImg())) {
            return;
        }

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, EXTRA_IMAGE);

        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, info);

        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());

//        activity.startActivity(intent);
    }

}
