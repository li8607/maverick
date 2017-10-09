package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.bean.SisterInfo;
import com.maverick.util.GlideUtil;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterTextHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected String TAG = getClass().getSimpleName();

    protected SisterInfo mSisterInfo;
    protected final ImageView image_head;
    protected final TextView time, name;
    protected final TextView content;
    protected final TextView text_ding_count, text_cai_count, text_share_count, text_comment_count;

    public SisterTextHolder(View itemView) {
        super(itemView);

        image_head = (ImageView) itemView.findViewById(R.id.image_head);
        time = (TextView) itemView.findViewById(R.id.time);
        content = (TextView) itemView.findViewById(R.id.content);

        text_ding_count = (TextView) itemView.findViewById(R.id.text_ding_count);
        text_cai_count = (TextView) itemView.findViewById(R.id.text_cai_count);
        text_share_count = (TextView) itemView.findViewById(R.id.text_share_count);
        text_comment_count = (TextView) itemView.findViewById(R.id.text_comment_count);
        name = (TextView) itemView.findViewById(R.id.name);
    }

    public void bindData(Context context, SisterInfo sisterInfo) {
        this.mSisterInfo = sisterInfo;
        GlideUtil.loadCircleImage(context, mSisterInfo.getProfile_image(), image_head);

        if (!TextUtils.isEmpty(sisterInfo.getName())) {
            name.setText(sisterInfo.getName().trim());
        }else {
            name.setText("");
        }

        if (!TextUtils.isEmpty(sisterInfo.getCreate_time())) {
            time.setText(sisterInfo.getCreate_time().trim());
        }else {
            time.setText("");
        }

        if (!TextUtils.isEmpty(sisterInfo.getText())) {
            Log.e(TAG, "sisterInfo.getText() = " + sisterInfo.getText());
            content.setText(sisterInfo.getText().trim());
        }else {
            content.setText("");
        }

        if (!TextUtils.isEmpty(sisterInfo.getLove())) {
            text_ding_count.setText(sisterInfo.getLove().trim());
        }else {
            text_ding_count.setText("0");
        }

        if (!TextUtils.isEmpty(sisterInfo.getHate())) {
            text_cai_count.setText(sisterInfo.getHate().trim());
        }else {
            text_cai_count.setText("0");
        }

        if (!TextUtils.isEmpty(sisterInfo.getShare())) {
            text_share_count.setText(sisterInfo.getShare().trim());
        } else {
            text_share_count.setText(text_share_count.getResources().getString(R.string.share));
        }

        if (!TextUtils.isEmpty(sisterInfo.getComment())) {
            text_comment_count.setText(sisterInfo.getComment().trim());
        } else {
            text_comment_count.setText(text_comment_count.getResources().getString(R.string.comment));
        }
    }


    @Override
    public void onClick(View v) {

    }
}
