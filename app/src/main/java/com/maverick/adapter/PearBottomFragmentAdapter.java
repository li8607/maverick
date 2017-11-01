package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.holder.PearImageViewHolder;
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
