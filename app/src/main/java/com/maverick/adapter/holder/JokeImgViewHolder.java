package com.maverick.adapter.holder;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.bean.BigImgInfo;
import com.maverick.bean.GifInfo;
import com.maverick.util.GlideUtil;
import com.maverick.weight.RatioImageView;

/**
 * Created by limingfei on 2017/10/4.
 */
public class JokeImgViewHolder extends JokeTextViewHolder {

    public final ImageView img;
    private GifInfo mGifInfo;
    private Context mContext;

    public JokeImgViewHolder(View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.img);
        img.setOnClickListener(this);
    }

    @Override
    public void bindData(Context context, GifInfo gifInfo) {
        super.bindData(context, gifInfo);
        this.mGifInfo = gifInfo;
        this.mContext = context;

        String title = gifInfo.getTitle();
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title.trim());
        } else {
            mTitle.setText("");
        }

        GlideUtil.loadImage(context, gifInfo.getImg(), img);
    }
}
