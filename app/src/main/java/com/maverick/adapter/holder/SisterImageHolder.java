package com.maverick.adapter.holder;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.bean.BigImgInfo;
import com.maverick.bean.SisterInfo;
import com.maverick.util.GlideUtil;
import com.maverick.weight.RatioImageView;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private String TAG = getClass().getSimpleName();

    private SisterInfo mSisterInfo;
    private final RatioImageView image;
    private final ImageView image_head;
    private final TextView time;
    private final TextView content;

    public SisterImageHolder(View itemView) {
        super(itemView);
        image = (RatioImageView) itemView.findViewById(R.id.image);
        image.setOriginalSize(1, 1);

        image_head = (ImageView) itemView.findViewById(R.id.image_head);
        time = (TextView) itemView.findViewById(R.id.time);
        content = (TextView) itemView.findViewById(R.id.content);

        image.setOnClickListener(this);
    }

    public void bindData(Context context, SisterInfo sisterInfo) {
        this.mSisterInfo = sisterInfo;
        GlideUtil.loadImage(context, mSisterInfo.getImage2(), image);
        GlideUtil.loadCircleImage(context, mSisterInfo.getProfile_image(), image_head);

        if (!TextUtils.isEmpty(sisterInfo.getCreate_time())) {
            time.setText(sisterInfo.getCreate_time());
        }

        if (!TextUtils.isEmpty(sisterInfo.getText())) {
            content.setText(sisterInfo.getText());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image:
                BigImgInfo bigImgInfo = new BigImgInfo();
                bigImgInfo.setImg(mSisterInfo.getImage2());
                DetailActivity.launch((Activity) v.getContext(), image, bigImgInfo);
                break;
        }
    }
}
