package com.maverick.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.maverick.R;

/**
 * Created by Administrator on 2017/11/28.
 */

public class UserViewHolder extends RecyclerView.ViewHolder {

    public final TextView title;
    public final View line;

    public UserViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.title);
        line = itemView.findViewById(R.id.line);
    }
}
