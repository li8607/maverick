package com.maverick.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.maverick.R;

/**
 * Created by ll on 2017/5/22.
 */
public class GlideUtil {

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).priority(Priority.HIGH).skipMemoryCache(true).fitCenter().error(R.mipmap.fail).diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(imageView);
    }
}
