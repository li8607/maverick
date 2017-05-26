package com.maverick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

        ImageView image_detail = (ImageView) findViewById(R.id.image_detail);

        ViewCompat.setTransitionName(image_detail, EXTRA_IMAGE);

        Intent intent = getIntent();
        GifInfo gifInfo = (GifInfo) intent.getSerializableExtra(EXTRA_IMAGE);

        GlideUtil.loadImage(this, gifInfo.img, image_detail);

    }

    public static void launch(Activity activity, View transitionView, GifInfo info) {

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, EXTRA_IMAGE);

        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, info);

        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
    }

}
