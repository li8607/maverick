package com.maverick.adapter.holder;

import android.app.Activity;
import android.content.Context;
import android.view.View;

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

    private final RatioImageView image;

    private Context mContext;

    private CaricatureInfo mInfo;

    public CaricatureOneImageViewHolder(View itemView) {
        super(itemView);
        image = (RatioImageView) itemView.findViewById(R.id.image);
        image.setOriginalSize(16, 9);

        image.setOnClickListener(this);
    }

    public void bindData(Context context, CaricatureInfo info) {
        super.bindData(context, info);
        this.mContext = context;
        this.mInfo = info;
        if (info.getThumbnailList() != null && info.getThumbnailList().size() > 0) {
            GlideUtil.loadImage(context, info.getThumbnailList().get(0), image);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.image:
                if (mContext != null && mContext instanceof Activity) {
                    DetailActivity.launch((Activity) mContext, v, BeanHelper.getBigImgInfo(mInfo));
                }
                break;
        }
    }
}
