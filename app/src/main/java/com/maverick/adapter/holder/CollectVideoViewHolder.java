package com.maverick.adapter.holder;

import android.content.Context;
import android.view.View;
import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/10/5.
 */
public class CollectVideoViewHolder extends CollectTextViewHolder {

    private Context mContext;
    private Collect mCollect;

    public CollectVideoViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Context context, Collect collect, boolean editState) {
        super.bindData(context, collect, editState);
        this.mCollect = collect;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //TODO 跳转视频。

    }
}
