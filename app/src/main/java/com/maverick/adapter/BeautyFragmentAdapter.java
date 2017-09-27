package com.maverick.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.bean.BeautyItemInfo;
import com.maverick.bean.BigImgInfo;
import com.maverick.model.HistoryModel;
import com.maverick.util.GlideUtil;
import com.maverick.weight.RatioImageView;

import java.util.List;

import cntv.greendaolibrary.dbbean.History;

/**
 * Created by Administrator on 2017/9/26.
 */
public class BeautyFragmentAdapter extends RecyclerView.Adapter {

    private List<BeautyItemInfo> mList;
    private Context mContext;

    public BeautyFragmentAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_beauty, parent, false);
        return new BeautyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BeautyHolder beautyHolder = (BeautyHolder) holder;
        beautyHolder.bindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<BeautyItemInfo> beautyItemInfos) {
        this.mList = beautyItemInfos;
    }

    public void setMoreData(List<BeautyItemInfo> beautyItemInfos) {
        if (mList != null) {
            mList.addAll(beautyItemInfos);
        }
    }

    public class BeautyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RatioImageView image;
        private final TextView title;
        private BeautyItemInfo mBeautyItemInfo;

        public BeautyHolder(View itemView) {
            super(itemView);
            image = (RatioImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);

            image.setOriginalSize(1, 1);
            itemView.setOnClickListener(this);
        }

        public void bindData(BeautyItemInfo beautyItemInfo) {
            this.mBeautyItemInfo = beautyItemInfo;
            GlideUtil.loadImage(mContext, beautyItemInfo.getUrl(), image);
        }

        @Override
        public void onClick(View v) {
            if (mBeautyItemInfo == null) {
                return;
            }

            History history = new History();
            history.setHistoryimage(mBeautyItemInfo.getUrl());
            history.setHistoryName(mBeautyItemInfo.getWho());
            history.setHistoryType("2");
            history.setHistoryTime(System.currentTimeMillis());
            HistoryModel.newInstance().insertHistoryDB(history);

            BigImgInfo bigImgInfo = new BigImgInfo();
            bigImgInfo.setImg(mBeautyItemInfo.getUrl());
            DetailActivity.launch((Activity) mContext, image, bigImgInfo);
        }
    }
}
