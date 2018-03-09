package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.holder.ChoiceViewHolder;

/**
 * Created by limingfei on 2018/3/9.
 */

public class ChoiceDialogAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private String[] mItems;
    private final LayoutInflater mLayoutInflater;

    public ChoiceDialogAdapter(Context context, String[] items) {
        this.mContext = context;
        this.mItems = items;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChoiceViewHolder(mLayoutInflater.inflate(R.layout.dialog_choice_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChoiceViewHolder choiceViewHolder = (ChoiceViewHolder) holder;
        choiceViewHolder.mTvTitle.setText(mItems[position]);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.length;
    }
}
