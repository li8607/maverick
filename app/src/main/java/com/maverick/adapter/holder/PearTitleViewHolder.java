package com.maverick.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.maverick.R;

/**
 * Created by Administrator on 2017/11/1.
 */

public class PearTitleViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;

    public PearTitleViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
    }

    public void bindData(String info) {
        title.setText(TextUtils.isEmpty(info) ? "" : info);
    }
}
