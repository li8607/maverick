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
    private final TextView text_ding_count, text_cai_count, text_share_count, text_comment_count;

    public SisterImageHolder(View itemView) {
        super(itemView);
        image = (RatioImageView) itemView.findViewById(R.id.image);
        image.setOriginalSize(16, 9);

        image_head = (ImageView) itemView.findViewById(R.id.image_head);
        time = (TextView) itemView.findViewById(R.id.time);
        content = (TextView) itemView.findViewById(R.id.content);

        text_ding_count = (TextView) itemView.findViewById(R.id.text_ding_count);
        text_cai_count = (TextView) itemView.findViewById(R.id.text_cai_count);
        text_share_count = (TextView) itemView.findViewById(R.id.text_share_count);
        text_comment_count = (TextView) itemView.findViewById(R.id.text_comment_count);

        image.setOnClickListener(this);
    }

    public void bindData(Context context, SisterInfo sisterInfo) {
        this.mSisterInfo = sisterInfo;
        GlideUtil.loadImage(context, mSisterInfo.getImage2(), image);
        GlideUtil.loadCircleImage(context, mSisterInfo.getProfile_image(), image_head);

        if (!TextUtils.isEmpty(sisterInfo.getCreate_time())) {
            time.setText(sisterInfo.getCreate_time().trim());
        }

        if (!TextUtils.isEmpty(sisterInfo.getText())) {
            Log.e(TAG, "sisterInfo.getText() = " + sisterInfo.getText());
            content.setText(sisterInfo.getText().trim());
        }

        if (!TextUtils.isEmpty(sisterInfo.getLove())) {
            text_ding_count.setText(sisterInfo.getLove().trim());
        }

        if (!TextUtils.isEmpty(sisterInfo.getHate())) {
            text_cai_count.setText(sisterInfo.getHate().trim());
        }

        if (!TextUtils.isEmpty(sisterInfo.getShare())) {
            text_share_count.setText(sisterInfo.getShare().trim());
        }

        if (!TextUtils.isEmpty(sisterInfo.getComment())) {
            text_comment_count.setText(sisterInfo.getComment().trim());
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
