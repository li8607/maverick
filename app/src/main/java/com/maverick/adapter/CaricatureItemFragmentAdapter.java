package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.holder.CaricatureItemViewHolder;
import com.maverick.adapter.holder.CaricatureOneImageViewHolder;
import com.maverick.bean.CaricatureInfo;

import java.util.List;

/**
 * Created by limingfei on 2017/10/20.
 */
public class CaricatureItemFragmentAdapter extends RecyclerView.Adapter {

    public static final int TYPE_TEXT = 1;
    public static final int TYPE_IMAGE = 2;

    private Context mContext;
    private List<CaricatureInfo> mList;

    public CaricatureItemFragmentAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CaricatureItemViewHolder holder;
        switch (viewType) {
            case TYPE_IMAGE:
                holder = new CaricatureOneImageViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_caricature_image_one, parent, false));
                break;
            default:
                holder = new CaricatureItemViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_caricature_text, parent, false));
                break;
        }
        holder.setOnListener(mOnListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CaricatureItemViewHolder caricatureViewHolder = (CaricatureItemViewHolder) holder;
        caricatureViewHolder.bindData(mContext, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (mList.get(position).getThumbnailList() != null && mList.get(position).getThumbnailList().size() > 0) {
            return TYPE_IMAGE;
        }
        return TYPE_TEXT;
    }

    public void setMoreData(List<CaricatureInfo> list) {
        if (mList != null) {
            mList.addAll(list);
        }
    }

    public void setData(List<CaricatureInfo> list) {
        this.mList = list;
    }

    public List<CaricatureInfo> getData() {
        return mList;
    }

    private CaricatureItemViewHolder.OnListener mOnListener;

    public void setOnListener(CaricatureItemViewHolder.OnListener listener) {
        this.mOnListener = listener;
    }
}
