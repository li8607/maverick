package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.holder.ChoiceViewHolder;
import com.maverick.dialog.ChoiceDialog;

/**
 * Created by limingfei on 2018/3/9.
 */

public class ChoiceDialogAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private String[] mItems;
    private String[] mItem;
    private final LayoutInflater mLayoutInflater;
    private int mType;

    public ChoiceDialogAdapter(Context context, String[] items, String[] item) {
        this.mContext = context;
        this.mItems = items;
        this.mItem = item;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChoiceViewHolder(mLayoutInflater.inflate(R.layout.dialog_choice_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ChoiceViewHolder choiceViewHolder = (ChoiceViewHolder) holder;
        choiceViewHolder.mTvTitle.setText(mItems[position]);
        switch (mType) {
            case ChoiceDialog.SINGLE:
            case ChoiceDialog.MULTI:

                if (mItem != null) {
                    for (int i = 0; i < mItem.length; i++) {
                        if (TextUtils.equals(mItem[i], mItems[position])) {
                            choiceViewHolder.mCheckbox.setChecked(true);
                            break;
                        }
                    }
                }
                choiceViewHolder.mCheckbox.setVisibility(View.VISIBLE);
                break;
            default:
                choiceViewHolder.mCheckbox.setVisibility(View.GONE);
                break;
        }
        if (mOnItemClickListener != null) {
            choiceViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder, position, !choiceViewHolder.mCheckbox.isChecked());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.length;
    }

    public void setType(int type) {
        this.mType = type;
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView.ViewHolder viewHolder, int position, boolean isChecked);
    }
}
