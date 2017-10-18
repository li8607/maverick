package com.maverick.adapter.holder;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.bean.BigImgInfo;
import com.maverick.bean.SisterInfo;
import com.maverick.util.GlideUtil;
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

    public void bindData(final Context context, SisterInfo sisterInfo) {
        super.bindData(context, sisterInfo);
        this.mSisterInfo = sisterInfo;
        GlideUtil.loadImage(context, mSisterInfo.getImage2(), image);

//        Glide.with(context).load(mSisterInfo.getImage2()).asBitmap().into(new SimpleTarget() {
//            @Override
//            public void onResourceReady(Object resource, GlideAnimation glideAnimation) {
//                if (!(resource instanceof Bitmap)) return;
//                Bitmap bitmap = (Bitmap) resource;
//
//                // Do bitmap blur here
//                Bitmap blurBitmap = FastBlur.doBlur(bitmap, 4, true);
//                image.setImageBitmap(blurBitmap);
//
//                // full image url
//                String fullImageUrl = mSisterInfo.getImage2();
////                Glide.with(context).load(fullImageUrl).asBitmap().into(new SimpleTarget() {
////                    @Override
////                    public void onResourceReady(Object resource, GlideAnimation glideAnimation) {
////                        if (!(resource instanceof Bitmap)) return;
////                        Bitmap fullBitmap = (Bitmap) resource;
////
////                        // full image task
////                        // do the full image task
////                        image.setImageBitmap(fullBitmap);
////                    }
////                });
//            }});


//        //下载图片保存到本地
//        Glide.with(context)
//                .load(mSisterInfo.getImage2()).downloadOnly(new SimpleTarget<File>() {
//
//            @Override
//            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
//
//                int oldPosition = getOldPosition();
//                int adapterPosition = getAdapterPosition();
//
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = true;
//                BitmapFactory.decodeFile(resource.getPath(), options);
//
//                int imageHeight;
//                int imageWidth = image.getMeasuredWidth();
//
//                if (options.outHeight >= imageWidth * 1.5) {
//                    image_text.setVisibility(View.VISIBLE);
//                    imageHeight = imageWidth * 1;
//                    image.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                } else {
//                    image_text.setVisibility(View.INVISIBLE);
////                    imageHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
//                    imageHeight = 0;
//                    image.setScaleType(ImageView.ScaleType.FIT_START);
//                }
//
//                ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
//                layoutParams.height = imageHeight;
//                image.setLayoutParams(layoutParams);
//
//                Glide.with(context)
//                        .load(resource.getPath())
//                        .dontAnimate()
//                        .error(R.drawable.empty_drawable)
//                        // 设置高斯模糊
//                        .bitmapTransform(new CropTransformation(context, image.getMeasuredWidth(), imageHeight, CropTransformation.CropType.TOP))
//                        .into(image);
//            }
//        });
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
