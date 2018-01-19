package com.maverick.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.maverick.R;

/**
 * Created by limingfei on 2018/1/19.
 */

public class ThemeHolder extends RecyclerView.ViewHolder {

    public View v_color;
    public TextView tv_color_title;
    public TextView tv_sure;

    public ThemeHolder(View itemView) {
        super(itemView);
        v_color = itemView.findViewById(R.id.v_color);
        tv_color_title = (TextView) itemView.findViewById(R.id.tv_color_title);
        tv_sure = (TextView) itemView.findViewById(R.id.tv_sure);
    }
}
