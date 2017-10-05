package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.bean.GifInfo;
import com.maverick.global.Tag;
import com.maverick.model.CollectModel;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/10/4.
 */
public class JokeTextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected final TextView mTitle;
    private GifInfo gifInfo;
    private final TextView time;
    private final View collect;

    public JokeTextViewHolder(final View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.title);
        time = (TextView) itemView.findViewById(R.id.time);
        collect = itemView.findViewById(R.id.collect);
        collect.setOnClickListener(this);
    }

    public void bindData(Context context, GifInfo gifInfo) {
        this.gifInfo = gifInfo;
        String text = gifInfo.text;
        if (!TextUtils.isEmpty(text)) {
            mTitle.setText(text.trim());
        }else {
            mTitle.setText("");
        }

        String time = gifInfo.ct;
        if (!TextUtils.isEmpty(time)) {
            this.time.setText(time.trim());
        }else {
            mTitle.setText("");
        }

        collect.setSelected(gifInfo.isCollect());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collect:

                Collect collect = new Collect();
                collect.setCollectTime(System.currentTimeMillis());
                collect.setCollectType("1");
                collect.setCollectImage(gifInfo.getImg());
                collect.setCollectCT(gifInfo.getCt());
                if (TextUtils.equals(gifInfo.getType(), Tag.JOKE_TEXT)) {
                    collect.setCollectItemType("1");
                    collect.setCollectName(gifInfo.getText());
                    collect.setCollectMajorKey(gifInfo.getText());
                } else if (TextUtils.equals(gifInfo.getType(), Tag.JOKE_IMG)) {
                    collect.setCollectItemType("2");
                    collect.setCollectMajorKey(gifInfo.getImg());
                    collect.setCollectName(gifInfo.getTitle());
                } else if (TextUtils.equals(gifInfo.getType(), Tag.JOKE_GIF)) {
                    collect.setCollectItemType("3");
                    collect.setCollectName(gifInfo.getTitle());
                    collect.setCollectMajorKey(gifInfo.getImg());
                }

                if (this.gifInfo.isCollect()) {
                    boolean result = CollectModel.newInstance().deleteCollectDB(collect);
                    if (result) {
                        this.collect.setSelected(false);
                        this.gifInfo.setCollect(false);
                    }
                } else {
                    boolean result = CollectModel.newInstance().insertCollectDB(collect);
                    if (result) {
                        this.collect.setSelected(true);
                        this.gifInfo.setCollect(true);
                    }
                }
                break;
        }
    }
}
