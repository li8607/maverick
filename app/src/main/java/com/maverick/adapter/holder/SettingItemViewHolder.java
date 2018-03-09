package com.maverick.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.maverick.R;

/**
 * Created by limingfei on 2018/3/9.
 */

public class SettingItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTitle;
    public final SwitchCompat mSwitch;
    public final View mTheme;

    public SettingItemViewHolder(View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.tv_title);
        mSwitch = itemView.findViewById(R.id.sc_switch);
        mTheme = itemView.findViewById(R.id.v_theme);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }
}
