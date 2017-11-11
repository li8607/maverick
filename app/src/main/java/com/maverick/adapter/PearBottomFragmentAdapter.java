package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.holder.PearCommentEmptyViewHolder;
import com.maverick.adapter.holder.PearCommentMoreViewHolder;
import com.maverick.adapter.holder.PearCommentViewHolder;
import com.maverick.adapter.holder.PearDetailViewHolder;
import com.maverick.adapter.holder.PearImageViewHolder;
import com.maverick.adapter.holder.PearTagViewHolder;
import com.maverick.adapter.holder.PearTitleViewHolder;
import com.maverick.bean.PearItemInfo;
import com.maverick.bean.PearVideoInfo;
import com.maverick.type.PearItemType;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class PearBottomFragmentAdapter extends RecyclerView.Adapter implements PearImageViewHolder.OnListener {

    private List<PearItemInfo> mList;
    private Context mContext;

    public PearBottomFragmentAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;

        switch (viewType) {
            case PearItemType.ITEM:
                PearImageViewHolder pearImageViewHolder = new PearImageViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_pear_image, parent, false));
                pearImageViewHolder.setOnListener(this);
                holder = pearImageViewHolder;
                break;
            case PearItemType.TITLE:
                PearTitleViewHolder pearTitleViewHolder = new PearTitleViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_pear_title, parent, false));
                holder = pearTitleViewHolder;
                break;
            case PearItemType.DETAIL:
                PearDetailViewHolder pearDetailViewHolder = new PearDetailViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_pear_detail, parent, false));
                holder = pearDetailViewHolder;
                break;
            case PearItemType.TAG:
                PearTagViewHolder pearTagViewHolder = new PearTagViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_pear_tag, parent, false));
                holder = pearTagViewHolder;
                break;
            case PearItemType.COMMENT:
                PearCommentViewHolder pearCommentViewHolder = new PearCommentViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_pear_comment, parent, false));
                holder = pearCommentViewHolder;
                break;
            case PearItemType.COMMENT_EMPTY:
                PearCommentEmptyViewHolder pearCommentEmptyViewHolder = new PearCommentEmptyViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_pear_comment_empty, parent, false));
                holder = pearCommentEmptyViewHolder;
                break;
            case PearItemType.COMMENT_MORE:
                PearCommentMoreViewHolder pearCommentMoreViewHolder = new PearCommentMoreViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_pear_comment_more, parent, false));
                holder = pearCommentMoreViewHolder;
                break;
            default:
                holder = new RecyclerView.ViewHolder(new View(parent.getContext())) {
                };
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PearImageViewHolder) {
            PearImageViewHolder pearImageViewHolder = (PearImageViewHolder) holder;
            pearImageViewHolder.bindData(mContext, mList.get(position).getPearVideoInfo());
        } else if (holder instanceof PearTitleViewHolder) {
            PearTitleViewHolder pearTitleViewHolder = (PearTitleViewHolder) holder;
            pearTitleViewHolder.bindData(mList.get(position).getTabTitle());
        } else if (holder instanceof PearDetailViewHolder) {
            PearDetailViewHolder pearDetailViewHolder = (PearDetailViewHolder) holder;
            pearDetailViewHolder.bindData(mContext, mList.get(position).getPearVideoDetailInfo());
        } else if (holder instanceof PearTagViewHolder) {
            PearTagViewHolder pearTagViewHolder = (PearTagViewHolder) holder;
            pearTagViewHolder.bindData(mContext, mList.get(position).getPearTagInfo());
        } else if (holder instanceof PearCommentViewHolder) {
            PearCommentViewHolder pearCommentViewHolder = (PearCommentViewHolder) holder;
            pearCommentViewHolder.bindData(mContext, mList.get(position).getCommentInfo());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<PearItemInfo> list) {
        this.mList = list;
    }

    @Override
    public void onItemClick(PearVideoInfo info) {

    }
}
