package com.maverick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
        final TextView title_detail = (TextView) findViewById(R.id.title_detail);
//        title_detail.setMovementMethod(ScrollingMovementMethod.getInstance());
        ViewCompat.setTransitionName(image_detail, EXTRA_IMAGE);
        Intent intent = getIntent();
        final GifInfo gifInfo = (GifInfo) intent.getSerializableExtra(EXTRA_IMAGE);

        if(!TextUtils.isEmpty(gifInfo.img)) {
            GlideUtil.loadImage(this, gifInfo.img, image_detail, new RequestListener() {
                @Override
                public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                    if(!TextUtils.isEmpty(gifInfo.title)) {
                        title_detail.setText(gifInfo.title);
                    }
                    return false;
                }
            });
        }else {
            image_detail.setVisibility(View.INVISIBLE);
            if(!TextUtils.isEmpty(gifInfo.text)) {
                title_detail.setText(gifInfo.text);
            }
        }
    }

    public static void launch(Activity activity, View transitionView, GifInfo info) {

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, EXTRA_IMAGE);

        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, info);

//        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
        activity.startActivity(intent);
    }

}
