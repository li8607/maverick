package com.maverick.adapter.holder;

import android.view.View;
import android.widget.ImageView;

import com.maverick.R;

/**
 * Created by Administrator on 2017/11/28.
 */

public class UserImageViewHolder extends UserViewHolder {

    public final ImageView image;

    public UserImageViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.image);
    }
}
