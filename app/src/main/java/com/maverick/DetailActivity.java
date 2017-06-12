package com.maverick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.maverick.bean.GifInfo;
import com.maverick.util.GlideUtil;

/**
 * Created by ll on 2017/5/25.
 */
public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE = "DetailActivity:image";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final ImageView image_detail = (ImageView) findViewById(R.id.image_detail);
        ViewCompat.setTransitionName(image_detail, EXTRA_IMAGE);
        Intent intent = getIntent();
        final GifInfo gifInfo = (GifInfo) intent.getSerializableExtra(EXTRA_IMAGE);

        GlideUtil.loadImage(this, gifInfo.img, image_detail, new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        });

    }

    public static void launch(Activity activity, View transitionView, GifInfo info) {

        if (info == null || TextUtils.isEmpty(info.img)) {
            return;
        }


        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, EXTRA_IMAGE);

        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, info);

//        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());

        activity.startActivity(intent);
    }

}
