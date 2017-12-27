package com.maverick.adapter.holder;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

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

    private final RatioImageView img;
    private GifInfo mGifInfo;
    private Context mContext;

    public JokeImgViewHolder(View itemView) {
        super(itemView);
        img = (RatioImageView) itemView.findViewById(R.id.img);
        img.setOnClickListener(this);
        img.setOriginalSize(4, 3);
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

    @Override
    public void onClick(View v) {
        super.onClick(v);


        switch (v.getId()) {
            case R.id.img:
                BigImgInfo bigImgInfo = new BigImgInfo();
                bigImgInfo.setImg(mGifInfo.img);
                DetailActivity.launch((Activity) mContext, img, bigImgInfo);
                break;
        }
    }
}
