package com.maverick.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.maverick.R;

/**
 * Created by limingfei on 2018/3/9.
 */

public class SettingTabViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTitle;

    public SettingTabViewHolder(View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.tv_title);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }
}
