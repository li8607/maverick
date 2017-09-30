package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.maverick.R;
import com.maverick.bean.SisterInfo;
import com.maverick.util.GlideUtil;
import com.maverick.weight.RatioImageView;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterImageHolder extends RecyclerView.ViewHolder {

    private SisterInfo mSisterInfo;
    private final RatioImageView image;

    public SisterImageHolder(View itemView) {
        super(itemView);
        image = (RatioImageView) itemView.findViewById(R.id.image);
        image.setOriginalSize(1, 1);
    }

    public void bindData(Context context, SisterInfo sisterInfo) {
        this.mSisterInfo = sisterInfo;
        GlideUtil.loadImage(context, mSisterInfo.getImage2(), image);
    }
}
