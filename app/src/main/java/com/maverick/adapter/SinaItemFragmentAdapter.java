package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.holder.CaricatureItemViewHolder;
import com.maverick.adapter.holder.SinaHolder;
import com.maverick.bean.SinaInfo;

import java.util.List;

/**
 * Created by limingfei on 2017/10/20.
 */
public class SinaItemFragmentAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<SinaInfo> mList;

    public SinaItemFragmentAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SinaHolder holder = new SinaHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sina, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SinaHolder sinaHolder = (SinaHolder) holder;
        sinaHolder.bindData(mContext, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setMoreData(List<SinaInfo> list) {
        if (mList != null) {
            mList.addAll(list);
        }
    }

    public void setData(List<SinaInfo> list) {
        this.mList = list;
    }

    private CaricatureItemViewHolder.OnListener mOnListener;

    public void setOnListener(CaricatureItemViewHolder.OnListener listener) {
        this.mOnListener = listener;
    }
}
