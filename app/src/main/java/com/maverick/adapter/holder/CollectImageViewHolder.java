package com.maverick.adapter.holder;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.bean.BigImgInfo;
import com.maverick.util.GlideUtil;
import com.maverick.weight.RatioImageView;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/10/5.
 */
public class CollectImageViewHolder extends CollectTextViewHolder {

    private final RatioImageView img;
    private Context mContext;
    private Collect mCollect;

    public CollectImageViewHolder(View itemView) {
        super(itemView);
        img = (RatioImageView) itemView.findViewById(R.id.img);
        img.setOnClickListener(this);
        img.setOriginalSize(4, 3);
    }

    @Override
    public void bindData(Context context, Collect collect, boolean editState) {
        super.bindData(context, collect, editState);
        this.mCollect = collect;
        this.mContext = context;
        GlideUtil.loadImage(context, collect.getCollectImage(), img);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);


        switch (v.getId()) {
            case R.id.img:
                BigImgInfo bigImgInfo = new BigImgInfo();
                bigImgInfo.setImg(mCollect.getCollectImage());
                DetailActivity.launch((Activity) mContext, img, bigImgInfo);
                break;
        }
    }
}
