package com.maverick.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.maverick.R;
import com.maverick.bean.SisterInfo;
import com.maverick.util.GlideUtil;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterImageHolder extends SisterTextHolder {


    private SisterInfo mSisterInfo;
    public ImageView image;

    public SisterImageHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.img);
    }

    public void bindData(final Context context, SisterInfo sisterInfo) {
        super.bindData(context, sisterInfo);
        this.mSisterInfo = sisterInfo;
        GlideUtil.loadImage(context, mSisterInfo.getImage2(), image);
    }
}
