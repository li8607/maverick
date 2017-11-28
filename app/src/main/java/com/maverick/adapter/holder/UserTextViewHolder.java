package com.maverick.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.maverick.R;

/**
 * Created by Administrator on 2017/11/28.
 */

public class UserTextViewHolder extends UserViewHolder {

    public final TextView value;

    public UserTextViewHolder(View itemView) {
        super(itemView);
        value = (TextView) itemView.findViewById(R.id.value);
    }
}
