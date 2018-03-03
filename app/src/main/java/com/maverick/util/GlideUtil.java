package com.maverick.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.maverick.R;

/**
 * Created by ll on 2017/5/22.
 */
public class GlideUtil {

    public static void loadImage(Context context, String url, ImageView imageView) {
        if (assertNotLoad(context, imageView)) {
            return;
        }

        Glide.with(context).load(url).thumbnail(0.1f).priority(Priority.HIGH).error(R.mipmap.fail).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView, SizeReadyCallback cb) {
        if (assertNotLoad(context, imageView)) {
            return;
        }

        Glide.with(context).load(url).priority(Priority.HIGH).skipMemoryCache(true).error(R.mipmap.fail).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView).getSize(cb);
    }

    public static void loadImage(Context context, String url, ImageView imageView, RequestListener requestListener) {
        if (assertNotLoad(context, imageView)) {
            return;
        }

        Glide.with(context).load(url).priority(Priority.HIGH).skipMemoryCache(true).fitCenter().error(R.mipmap.fail).diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().listener(requestListener).into(imageView);
    }

    public static void loadCircleImage(Context context, String imageUrl, ImageView imageView) {
        if (assertNotLoad(context, imageView)) {
            return;
        }

        Glide.with(context).load(imageUrl)
                .fitCenter()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(R.mipmap.ic_header)
                .error(R.mipmap.ic_header)
                .crossFade()
                .into(imageView);
    }

    private static boolean assertNotLoad(Context context, ImageView view) {

        if (context == null || view == null) {
            return true;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (context instanceof FragmentActivity && ((FragmentActivity) context).isDestroyed()) {
                return true;
            }
            if (context instanceof Activity && ((Activity) context).isDestroyed()) {
                return true;
            }
        }

        return false;
    }

    public static class CropCircleTransformation implements Transformation<Bitmap> {

        private BitmapPool mBitmapPool;

        public CropCircleTransformation(Context context) {
            this(Glide.get(context).getBitmapPool());
        }

        public CropCircleTransformation(BitmapPool pool) {
            this.mBitmapPool = pool;
        }

        @Override
        public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
            Bitmap source = resource.get();
            int size = Math.min(source.getWidth(), source.getHeight());

            int width = (source.getWidth() - size) / 2;
            int height = (source.getHeight() - size) / 2;

            Bitmap bitmap = mBitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader =
                    new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            if (width != 0 || height != 0) {
                // source isn't square, move viewport to center
                Matrix matrix = new Matrix();
                matrix.setTranslate(-width, -height);
                shader.setLocalMatrix(matrix);
            }
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            return BitmapResource.obtain(bitmap, mBitmapPool);
        }

        @Override
        public String getId() {
            return "CropCircleTransformation()";
        }
    }
}
