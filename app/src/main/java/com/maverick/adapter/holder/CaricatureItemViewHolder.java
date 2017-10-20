package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.bean.CaricatureInfo;

/**
 * Created by limingfei on 2017/10/20.
 */
public class CaricatureItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final TextView title;

    public CaricatureItemViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
    }

    public void bindData(Context context, CaricatureInfo info) {
        if (!TextUtils.isEmpty(info.getTitle())) {
            title.setText(info.getTitle());
        } else {
            title.setText("");
        }
    }

    @Override
    public void onClick(View v) {

    }
}
