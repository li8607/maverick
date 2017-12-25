package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.holder.PearBannerViewHolder;
import com.maverick.adapter.holder.PearGalleryViewHolder;
import com.maverick.adapter.holder.PearImageViewHolder;
import com.maverick.base.ThemeAdapter;
import com.maverick.base.ThemedViewHolder;
import com.maverick.bean.PearVideoInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class PearItemFragmentAdapter extends ThemeAdapter implements PearImageViewHolder.OnListener, PearGalleryViewHolder.OnListener, PearBannerViewHolder.OnListener {

    public static final int TYPE_GALLERY = 1;
    public static final int TYPE_IMAGE = 2;
    public static final int TYPE_BANNER = 3;

    private List<PearVideoInfo> mHotList;
    private List<PearVideoInfo> mList;

    private Context mContext;

    public PearItemFragmentAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case TYPE_GALLERY:
                PearGalleryViewHolder pearGalleryViewHolder = new PearGalleryViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_pear_gallery, parent, false));
                pearGalleryViewHolder.setOnListener(this);
                holder = pearGalleryViewHolder;
                break;
            case TYPE_BANNER:
                PearBannerViewHolder pearBannerViewHolder = new PearBannerViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_pear_banner, parent, false));
                pearBannerViewHolder.setOnListener(this);
                holder = pearBannerViewHolder;
                break;
            case TYPE_IMAGE:
                PearImageViewHolder pearImageViewHolder = new PearImageViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_pear_image, parent, false));
                pearImageViewHolder.setOnListener(this);
                holder = pearImageViewHolder;
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
        if (holder instanceof ThemedViewHolder) {
            super.onBindViewHolder((ThemedViewHolder) holder, position);
        }
        if (holder instanceof PearGalleryViewHolder) {
            PearGalleryViewHolder pearGalleryViewHolder = (PearGalleryViewHolder) holder;
            pearGalleryViewHolder.bindData(mContext, mHotList);
        } else if (holder instanceof PearImageViewHolder) {
            PearImageViewHolder pearImageViewHolder = (PearImageViewHolder) holder;
            pearImageViewHolder.bindData(mContext, mList.get(isHeader() ? position - 1 : position));
        } else if (holder instanceof PearBannerViewHolder) {
            PearBannerViewHolder pearBannerViewHolder = (PearBannerViewHolder) holder;
            pearBannerViewHolder.bindData(mContext, mHotList);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader() && position == 0) {
//            return TYPE_GALLERY;
            return TYPE_BANNER;
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

    @Override
    public void onItemClick(int position, PearVideoInfo info) {
        if (mOnListener != null) {
            mOnListener.onItemClick(info);
        }
    }

    private OnListener mOnListener;

    public void setOnListener(OnListener listener) {
        this.mOnListener = listener;
    }

    @Override
    public void onItemClick(PearVideoInfo info) {
        if (mOnListener != null) {
            mOnListener.onItemClick(info);
        }
    }

    public interface OnListener {
        void onItemClick(PearVideoInfo info);
    }
}
