package com.maverick.adapter.holder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.base.ThemedViewHolder;

import cntv.themelibrary.ThemeHelper;

/**
 * Created by Administrator on 2017/12/1.
 */

public class MyItemViewHolder extends ThemedViewHolder {

    public final ImageView imageView;
    public final TextView title;
    public final View line;

    public MyItemViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        title = (TextView) itemView.findViewById(R.id.title);
        line = itemView.findViewById(R.id.line);
    }

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        title.setTextColor(themeHelper.getTextColor());
        line.setBackgroundColor(themeHelper.getTextColor());
        ((CardView) itemView).setCardBackgroundColor(themeHelper.getCardBackgroundColor());
    }
}
