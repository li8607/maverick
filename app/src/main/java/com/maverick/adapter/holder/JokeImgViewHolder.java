package com.maverick.adapter.holder;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.bean.BigImgInfo;
import com.maverick.bean.GifInfo;
import com.maverick.model.HistoryModel;
import com.maverick.util.GlideUtil;
import com.maverick.weight.RatioImageView;

import cntv.greendaolibrary.dbbean.History;

/**
 * Created by limingfei on 2017/10/4.
 */
public class JokeImgViewHolder extends JokeTextViewHolder implements View.OnClickListener {

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
        GlideUtil.loadImage(context, gifInfo.getImg(), img);
    }

    @Override
    public void onClick(View v) {
        History history = new History();
        history.setHistoryimage(mGifInfo.img);
        history.setHistoryName(mGifInfo.title);
        history.setHistoryType("1");
        history.setHistoryItemType("2");
        history.setHistoryTime(System.currentTimeMillis());
        HistoryModel.newInstance().insertHistoryDB(history);

        BigImgInfo bigImgInfo = new BigImgInfo();
        bigImgInfo.setImg(mGifInfo.img);
        DetailActivity.launch((Activity) mContext, img, bigImgInfo);
    }
}
