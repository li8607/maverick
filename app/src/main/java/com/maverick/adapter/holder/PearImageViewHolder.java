package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.maverick.R;
import com.maverick.bean.PearVideoInfo;
import com.maverick.util.GlideUtil;
import com.maverick.weight.RatioImageView;

/**
 * Created by Administrator on 2017/10/27.
 */

public class PearImageViewHolder extends RecyclerView.ViewHolder {

    private final RatioImageView image;

    public PearImageViewHolder(View itemView) {
        super(itemView);
        image = (RatioImageView) itemView.findViewById(R.id.image);
        image.setOriginalSize(16, 9);
    }

    public void bindData(Context context, PearVideoInfo info) {
        String pic = info.getPic();
        GlideUtil.loadImage(context, pic, image);
    }
}
