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

    public SisterImageHolder(View itemView) {
        super(itemView);
        image = (RatioImageView) itemView.findViewById(R.id.image);
        image.setOriginalSize(16, 9);

        image.setOnClickListener(this);
    }

    public void bindData(Context context, SisterInfo sisterInfo) {
        super.bindData(context, sisterInfo);
        this.mSisterInfo = sisterInfo;
        GlideUtil.loadImage(context, mSisterInfo.getImage2(), image);
//        Glide.with(context).load(mSisterInfo.getImage2()).priority(Priority.HIGH).skipMemoryCache(true).error(R.mipmap.fail).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(image).getSize(new SizeReadyCallback() {
//            @Override
//            public void onSizeReady(int width, int height) {
//                Log.e(TAG, "width = " + width);
//                Log.e(TAG, "height = " + height);
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
