package com.maverick.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.bean.GifInfo;
import com.maverick.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ll on 2017/5/18.
 */
public class GifAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GifInfo> mList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_git_item, parent, false);
        return new GifAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GifAdapterViewHolder mGifAdapterViewHolder = (GifAdapterViewHolder) holder;
        mGifAdapterViewHolder.setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<GifInfo> list) {
        if (list != null) {
            this.mList = list;
        }
    }

    public void setMoreData(List<GifInfo> list) {
        if (list != null) {
            this.mList.addAll(list);
        }
    }

    public class GifAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, RequestListener {

        private final ImageView mImageView;
        private final TextView mTextView;
        private Context mContext;
        private String imgUrl;
        private final ProgressBar mLoading;
        private GifInfo mGifInfo;

        public GifAdapterViewHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mTextView = (TextView) itemView.findViewById(R.id.title);
            mLoading = (ProgressBar) itemView.findViewById(R.id.loading);
            itemView.setOnClickListener(this);
        }

        public void setData(GifInfo gifInfo) {
            mGifInfo = gifInfo;
            if (!TextUtils.isEmpty(gifInfo.title)) {
                mTextView.setText(gifInfo.title);
            }

            imgUrl = gifInfo.img;
            mLoading.setVisibility(View.VISIBLE);
            GlideUtil.loadImage(mContext, imgUrl, mImageView, this);
        }

        @Override
        public void onClick(View v) {
            DetailActivity.launch((Activity) mContext, mImageView, mGifInfo);
        }

        @Override
        public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
            mLoading.setVisibility(View.INVISIBLE);
            return false;
        }
    }
}
