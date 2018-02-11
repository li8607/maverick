package com.maverick.adapter.holder;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.R;

/**
 * Created by Administrator on 2017/12/1.
 */

public class MyItemViewHolder extends RecyclerView.ViewHolder {

    public final AppCompatImageView imageView;
    public final TextView title;
    public final View line;

    public MyItemViewHolder(View itemView) {
        super(itemView);
        imageView =  itemView.findViewById(R.id.image);
        title =  itemView.findViewById(R.id.title);
        line = itemView.findViewById(R.id.line);
    }
}
