package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.bean.PearVideoDetailInfoPostChildItem;
import com.maverick.util.GlideUtil;

/**
 * Created by Administrator on 2017/11/9.
 */

public class PearCommentViewHolder extends RecyclerView.ViewHolder {

    private final ImageView user_image;
    private final TextView user_name;
    private final TextView user_comment;
    private final TextView comment_time;

    public PearCommentViewHolder(View itemView) {
        super(itemView);

        user_image = (ImageView) itemView.findViewById(R.id.user_image);
        user_name = (TextView) itemView.findViewById(R.id.user_name);
        user_comment = (TextView) itemView.findViewById(R.id.user_comment);
        comment_time = (TextView) itemView.findViewById(R.id.comment_time);


    }

    public void bindData(Context context, PearVideoDetailInfoPostChildItem info) {

        if (info.getUserInfo() != null) {
            GlideUtil.loadCircleImage(context, info.getUserInfo().getPic(), user_image);
            user_name.setText(TextUtils.isEmpty(info.getUserInfo().getNickname()) ? "" : info.getUserInfo().getNickname());
        }

        user_comment.setText(TextUtils.isEmpty(info.getContent()) ? "" : info.getContent());

        comment_time.setText(TextUtils.isEmpty(info.getPubTime()) ? "" : info.getPubTime());
    }
}
