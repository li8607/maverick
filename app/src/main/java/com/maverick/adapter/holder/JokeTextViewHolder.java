package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.bean.GifInfo;

/**
 * Created by limingfei on 2017/10/4.
 */
public class JokeTextViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTitle;
    private GifInfo gifInfo;
    private final TextView time;

    public JokeTextViewHolder(final View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.title);
        time = (TextView) itemView.findViewById(R.id.time);
    }

    public void bindData(Context context, GifInfo gifInfo) {
        this.gifInfo = gifInfo;
        String text = gifInfo.text;
        if (!TextUtils.isEmpty(text)) {
            mTitle.setText(text.trim());
        }

        String time = gifInfo.ct;
        if (!TextUtils.isEmpty(time)) {
            this.time.setText(time.trim());
        }
    }
}
