package com.maverick.adapter.holder;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.bean.CaricatureInfo;
import com.maverick.hepler.BeanHelper;
import com.maverick.util.GlideUtil;
import com.maverick.weight.RatioImageView;

/**
 * Created by limingfei on 2017/10/20.
 */
public class CaricatureOneImageViewHolder extends CaricatureItemViewHolder {

    private final RatioImageView image_0, image_1, image_2;

    private Context mContext;

    private CaricatureInfo mInfo;

    public CaricatureOneImageViewHolder(View itemView) {
        super(itemView);
        image_0 = (RatioImageView) itemView.findViewById(R.id.image_0);
        image_0.setOriginalSize(16, 9);
        image_0.setOnClickListener(this);

        image_1 = (RatioImageView) itemView.findViewById(R.id.image_1);
        image_1.setOriginalSize(16, 9);
        image_1.setOnClickListener(this);

        image_2 = (RatioImageView) itemView.findViewById(R.id.image_2);
        image_2.setOriginalSize(16, 9);
        image_2.setOnClickListener(this);
    }

    public void bindData(Context context, CaricatureInfo info) {
        super.bindData(context, info);
        this.mContext = context;
        this.mInfo = info;

        if (info.getThumbnailList() != null && info.getThumbnailList().size() > 0) {
            if (info.getThumbnailList().size() < 2) {
                LinearLayout.LayoutParams image_1LP = (LinearLayout.LayoutParams) image_1.getLayoutParams();
                image_1LP.width = 0;
                image_1.setLayoutParams(image_1LP);

                LinearLayout.LayoutParams image_2LP = (LinearLayout.LayoutParams) image_2.getLayoutParams();
                image_2LP.width = 0;
                image_2.setLayoutParams(image_2LP);

                GlideUtil.loadImage(context, info.getThumbnailList().get(0), image_0);
            } else if (info.getThumbnailList().size() < 3) {
                LinearLayout.LayoutParams image_1LP = (LinearLayout.LayoutParams) image_1.getLayoutParams();
                image_1LP.width = 0;
                image_1LP.weight = 1;
                image_1.setLayoutParams(image_1LP);

                LinearLayout.LayoutParams image_2LP = (LinearLayout.LayoutParams) image_2.getLayoutParams();
                image_2LP.width = 0;
                image_2.setLayoutParams(image_2LP);

                GlideUtil.loadImage(context, info.getThumbnailList().get(0), image_0);
                GlideUtil.loadImage(context, info.getThumbnailList().get(1), image_1);
            } else {
                LinearLayout.LayoutParams image_1LP = (LinearLayout.LayoutParams) image_1.getLayoutParams();
                image_1LP.width = 0;
                image_1LP.weight = 1;
                image_1.setLayoutParams(image_1LP);

                LinearLayout.LayoutParams image_2LP = (LinearLayout.LayoutParams) image_2.getLayoutParams();
                image_2LP.width = 0;
                image_2LP.weight = 1;
                image_2.setLayoutParams(image_2LP);

                GlideUtil.loadImage(context, info.getThumbnailList().get(0), image_0);
                GlideUtil.loadImage(context, info.getThumbnailList().get(1), image_1);
                GlideUtil.loadImage(context, info.getThumbnailList().get(2), image_2);
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.image_0:
            case R.id.image_1:
            case R.id.image_2:
                if (mContext != null && mContext instanceof Activity) {
                    DetailActivity.launch((Activity) mContext, v, BeanHelper.getBigImgInfo(mInfo));
                }
                break;
        }
    }
}
