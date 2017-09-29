package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.maverick.R;
import com.maverick.util.GlideUtil;
import com.maverick.weight.RatioImageView;

import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/9/29.
 */
public class CollectItemBeautyFragmentAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private List<Collect> mList;

    private boolean editState;

    public CollectItemBeautyFragmentAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CollectBeautyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect_beauty_image, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CollectBeautyViewHolder collectBeautyViewHolder = (CollectBeautyViewHolder) holder;
        collectBeautyViewHolder.bindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<Collect> list) {
        this.mList = list;
    }

    public void setEditState(boolean editState) {
        this.editState = editState;
    }

    public List<Collect> getData() {
        return mList;
    }

    public class CollectBeautyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        private final RatioImageView image;
        private final CheckBox checkbox;
        private Collect collect;

        public CollectBeautyViewHolder(View itemView) {
            super(itemView);
            image = (RatioImageView) itemView.findViewById(R.id.image);
            image.setOriginalSize(1, 1);

            itemView.setOnClickListener(this);

            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            checkbox.setOnCheckedChangeListener(this);
        }

        public void bindData(Collect collect) {
            this.collect = collect;
            GlideUtil.loadImage(mContext, collect.getCollectImage(), image);
            checkbox.setVisibility(editState ? View.VISIBLE : View.INVISIBLE);
            checkbox.setChecked(collect.isCheck());
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            this.collect.setCheck(isChecked);
        }

        @Override
        public void onClick(View v) {
            checkbox.setChecked(!checkbox.isChecked());
            if(mOnAdapterListener != null) {
                mOnAdapterListener.onItemClick(getAdapterPosition(), collect);
            }
        }

        public void setCheckViewVisible(int visible, boolean check) {
            this.checkbox.setVisibility(visible);
            this.checkbox.setChecked(check);
        }

        public void setCheck(boolean check) {
            this.checkbox.setChecked(check);
        }
    }

    private OnAdapterListener mOnAdapterListener;

    public void setOnAdapterListener(OnAdapterListener listener) {
        this.mOnAdapterListener = listener;
    }

    public interface OnAdapterListener {
        void onItemClick(int position, Collect collect);
    }
}
