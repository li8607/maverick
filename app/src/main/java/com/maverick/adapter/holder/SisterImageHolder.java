package com.maverick.adapter.holder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.bean.BigImgInfo;
import com.maverick.bean.SisterInfo;
import com.maverick.weight.RatioImageView;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterImageHolder extends SisterTextHolder {


    private SisterInfo mSisterInfo;
    private final RatioImageView image;
    private final View image_text;

    public SisterImageHolder(View itemView) {
        super(itemView);
        image = (RatioImageView) itemView.findViewById(R.id.image);
        image.setOriginalSize(16, 9);

        image.setOnClickListener(this);

        image_text = itemView.findViewById(R.id.image_text);
    }

    public void bindData(Context context, SisterInfo sisterInfo) {
        super.bindData(context, sisterInfo);
        this.mSisterInfo = sisterInfo;
//        GlideUtil.loadImage(context, mSisterInfo.getImage2(), image);
        Glide.with(context).load(mSisterInfo.getImage2()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Log.e(TAG, "resource.getWidth() = " + resource.getWidth());
                Log.e(TAG, "resource.getHeight() = " + resource.getHeight());
                if(resource.getHeight() / resource.getWidth() > 3){
                    image_text.setVisibility(View.VISIBLE);
                }else {
                    image_text.setVisibility(View.INVISIBLE);
                }

                image.setImageBitmap(ImageCrop(resource, true));
            }
        });
    }

    /**
     * 按正方形裁切图片
     */
    public static Bitmap ImageCrop(Bitmap bitmap, boolean isRecycled)
    {

        if (bitmap == null)
        {
            return null;
        }

        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

        int retX = w > h ? (w - h) / 2 : 0;// 基于原图，取正方形左上角x坐标
        int retY = w > h ? 0 : (h - w) / 2;

        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null,
                false);
        if (isRecycled && bitmap != null && !bitmap.equals(bmp)
                && !bitmap.isRecycled())
        {
            bitmap.recycle();
            bitmap = null;
        }

        // 下面这句是关键
        return bmp;// Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null,
        // false);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.image:
                BigImgInfo bigImgInfo = new BigImgInfo();
                bigImgInfo.setImg(mSisterInfo.getImage2());
                DetailActivity.launch((Activity) v.getContext(), image, bigImgInfo);
                break;
        }
    }
}
