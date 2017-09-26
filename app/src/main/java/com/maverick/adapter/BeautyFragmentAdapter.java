package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.bean.BeautyItemInfo;
import com.maverick.util.GlideUtil;

import java.util.List;

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

    public class BeautyHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView title;

        public BeautyHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
        }

        public void bindData(BeautyItemInfo beautyItemInfo) {
            GlideUtil.loadImage(mContext, beautyItemInfo.getUrl(), image);
        }
    }
}
