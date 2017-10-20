package com.maverick.adapter.holder;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.bean.BigImgInfo;
import com.maverick.bean.SisterInfo;
import com.maverick.hepler.BeanHepler;
import com.maverick.util.GlideUtil;
import com.maverick.weight.RatioImageView;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterImageHolder extends SisterTextHolder {


    private SisterInfo mSisterInfo;
    private final RatioImageView image;
    private final View image_text;
    private final View loading;

    public SisterImageHolder(View itemView) {
        super(itemView);
        image = (RatioImageView) itemView.findViewById(R.id.image);
        image.setOriginalSize(16, 9);

        image.setOnClickListener(this);

        image_text = itemView.findViewById(R.id.image_text);
        loading = itemView.findViewById(R.id.loading);
    }

    public void bindData(final Context context, SisterInfo sisterInfo) {
        super.bindData(context, sisterInfo);
        this.mSisterInfo = sisterInfo;
        GlideUtil.loadImage(context, mSisterInfo.getImage2(), image);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.image:
                if (mSisterInfo == null) {
                    return;
                }
                BigImgInfo bigImgInfo = new BigImgInfo();
                bigImgInfo.setImg(mSisterInfo.getImage2());
                bigImgInfo.setTitle(mSisterInfo.getText());
                bigImgInfo.setWebUrl(mSisterInfo.getWeixin_url());
                bigImgInfo.setCollect(BeanHepler.getCollect(mSisterInfo));
                DetailActivity.launch((Activity) v.getContext(), image, bigImgInfo);
                break;
        }
    }
}
