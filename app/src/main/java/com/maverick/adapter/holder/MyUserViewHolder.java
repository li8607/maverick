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

public class MyUserViewHolder extends ThemedViewHolder {

    public final ImageView imageView;
    public final TextView nickname, username;

    public MyUserViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        nickname = (TextView) itemView.findViewById(R.id.nickname);
        username = (TextView) itemView.findViewById(R.id.username);
    }

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        nickname.setTextColor(themeHelper.getTextColor());
        username.setTextColor(themeHelper.getSubTextColor());
        ((CardView) itemView).setCardBackgroundColor(themeHelper.getCardBackgroundColor());
    }
}
