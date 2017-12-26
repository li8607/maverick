package com.maverick.adapter.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.base.ThemedViewHolder;

import cntv.themelibrary.ThemeHelper;

/**
 * Created by Administrator on 2017/11/1.
 */

public class PearTitleViewHolder extends ThemedViewHolder {

    private final TextView title;

    public PearTitleViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
    }

    public void bindData(String info) {
        title.setText(TextUtils.isEmpty(info) ? "" : info);
    }

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        title.setTextColor(themeHelper.getTextColor());
    }
}
