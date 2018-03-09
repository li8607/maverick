package com.maverick.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.maverick.R;

/**
 * Created by limingfei on 2018/3/9.
 */

public class ChoiceViewHolder extends RecyclerView.ViewHolder {

    public final TextView mTvTitle;
    public final CheckBox mCheckbox;

    public ChoiceViewHolder(View itemView) {
        super(itemView);

        mTvTitle = itemView.findViewById(R.id.tv_title);
        mCheckbox = itemView.findViewById(R.id.checkbox);
    }
}
