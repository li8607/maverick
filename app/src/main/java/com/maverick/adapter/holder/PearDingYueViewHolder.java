package com.maverick.adapter.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.base.ThemedViewHolder;
import com.maverick.bean.PearVideoInfoNode;
import com.maverick.util.GlideUtil;

import cntv.themelibrary.ThemeHelper;

/**
 * Created by limingfei on 2017/12/26.
 */

public class PearDingYueViewHolder extends ThemedViewHolder {

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

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        dingyue_btn.setTextColor(themeHelper.getTextColor());
        dingyue_title.setTextColor(themeHelper.getTextColor());
        dingyue_huati.setTextColor(themeHelper.getSubTextColor());
    }
}
