package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.bean.PearVideoInfoNode;
import com.maverick.util.GlideUtil;

/**
 * Created by limingfei on 2017/12/26.
 */

public class PearDingYueViewHolder extends RecyclerView.ViewHolder {

    private final ImageView dingyue_image;
    private final TextView dingyue_btn, dingyue_title, dingyue_huati;

    public PearDingYueViewHolder(View view) {
        super(view);

        dingyue_btn = (TextView) itemView.findViewById(R.id.dingyue_btn);
        dingyue_title = (TextView) itemView.findViewById(R.id.dingyue_title);
        dingyue_huati = (TextView) itemView.findViewById(R.id.dingyue_huati);
        dingyue_image = (ImageView) itemView.findViewById(R.id.dingyue_image);
    }

    public void bindData(Context context, PearVideoInfoNode infoNode) {
        if (infoNode != null) {
            GlideUtil.loadCircleImage(context, infoNode.getLogoImg(), dingyue_image);
            dingyue_title.setText(TextUtils.isEmpty(infoNode.getName()) ? "" : infoNode.getName());
            dingyue_huati.setText(TextUtils.isEmpty(infoNode.getDesc()) ? "" : infoNode.getDesc());
        } else {
            dingyue_title.setText("");
            dingyue_huati.setText("");
        }
    }
}
