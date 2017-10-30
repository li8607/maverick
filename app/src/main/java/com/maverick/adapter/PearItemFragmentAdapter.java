package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.holder.PearGalleryViewHolder;
import com.maverick.adapter.holder.PearImageViewHolder;
import com.maverick.bean.PearVideoInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class PearItemFragmentAdapter extends RecyclerView.Adapter {

    public static final int TYPE_GALLERY = 1;
    public static final int TYPE_IMAGE = 2;

    private List<PearVideoInfo> mHotList;
    private List<PearVideoInfo> mList;

    private Context mContext;

    public PearItemFragmentAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case TYPE_GALLERY:
                holder = new PearGalleryViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_pear_gallery, parent, false));
                break;
            case TYPE_IMAGE:
                holder = new PearImageViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_pear_image, parent, false));
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
        if (holder instanceof PearGalleryViewHolder) {
            PearGalleryViewHolder pearGalleryViewHolder = (PearGalleryViewHolder) holder;
            pearGalleryViewHolder.bindData(mContext, mHotList);
        } else if (holder instanceof PearImageViewHolder) {
            PearImageViewHolder pearImageViewHolder = (PearImageViewHolder) holder;
            pearImageViewHolder.bindData(mContext, mList.get(isHeader() ? position - 1 : position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader() && position == 0) {
            return TYPE_GALLERY;
        } else {
            return TYPE_IMAGE;
        }
    }

    public boolean isHeader() {
        if (mHotList != null && mHotList.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return mList == null ? isHeader() ? 1 : 0 : isHeader() ? mList.size() + 1 : mList.size();
    }

    public void setData(List<PearVideoInfo> hotList, List<PearVideoInfo> list) {
        this.mHotList = hotList;
        this.mList = list;
    }

    public void setMoreData(List<PearVideoInfo> list) {
        if (mList != null && list != null) {
            mList.addAll(list);
        }
    }
}
