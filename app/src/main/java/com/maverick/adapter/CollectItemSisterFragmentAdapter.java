package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.holder.CollectJokeTextViewHolder;
import com.maverick.adapter.holder.CollectSisterImageViewHolder;
import com.maverick.adapter.holder.CollectSisterVideoViewHolder;
import com.maverick.type.CollectType;

import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/10/5.
 */
public class CollectItemSisterFragmentAdapter extends RecyclerView.Adapter {

    private static final int TYPE_TEXT = 1;
    private static final int TYPE_IMG = 2;
    private static final int TYPE_VIDEO = 3;

    private List<Collect> mList;

    private Context mContext;

    private boolean editState;

    public CollectItemSisterFragmentAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;

        switch (viewType) {
            case TYPE_TEXT:
                CollectJokeTextViewHolder collectJokeTextViewHolder = new CollectJokeTextViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_collect_sister_text, parent, false));
                collectJokeTextViewHolder.setOnCollectJokeTextViewHolderListener(mOnCollectJokeTextViewHolderListener);
                holder = collectJokeTextViewHolder;
                break;
            case TYPE_IMG:
                CollectSisterImageViewHolder collectSisterImageViewHolder = new CollectSisterImageViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_collect_sister_image, parent, false));
                collectSisterImageViewHolder.setOnCollectJokeTextViewHolderListener(mOnCollectJokeTextViewHolderListener);
                holder = collectSisterImageViewHolder;
                break;
            case TYPE_VIDEO:
                CollectSisterVideoViewHolder collectSisterVideoViewHolder = new CollectSisterVideoViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_collect_sister_video, parent, false));
                collectSisterVideoViewHolder.setOnCollectJokeTextViewHolderListener(mOnCollectJokeTextViewHolderListener);
                holder = collectSisterVideoViewHolder;
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
        if (holder instanceof CollectJokeTextViewHolder) {
            CollectJokeTextViewHolder collectJokeTextViewHolder = (CollectJokeTextViewHolder) holder;
            collectJokeTextViewHolder.bindData(mContext, mList.get(position), editState);
        }
    }

    @Override
    public int getItemViewType(int position) {

        Collect collect = mList.get(position);

        String type = collect.getCollectType();
        String itemType = collect.getCollectItemType();

        if (TextUtils.equals(type, CollectType.CARICATURE)) {
            return TYPE_IMG;
        } else if (TextUtils.equals(type, CollectType.SISTER)) {
            if (TextUtils.equals(itemType, CollectType.SISTER_IMAGE)) {
                //图片
                return TYPE_IMG;
            } else if (TextUtils.equals(itemType, CollectType.SISTER_TEXT)) {
                //段子
                return TYPE_TEXT;
            } else if (TextUtils.equals(itemType, CollectType.SISTER_AUDIO)) {
                //声音
                return TYPE_VIDEO;
            } else if (TextUtils.equals(itemType, CollectType.SISTER_VIDEO)) {
                //视频
                return TYPE_VIDEO;
            }
        }
        return TYPE_TEXT;
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

    private CollectJokeTextViewHolder.OnCollectJokeTextViewHolderListener mOnCollectJokeTextViewHolderListener = new CollectJokeTextViewHolder.OnCollectJokeTextViewHolderListener() {
        @Override
        public void onItemClick(int position, Collect collect) {
            if (editState) {
                return;
            }
        }
    };

    public void setOnCollectJokeTextViewHolderListener(CollectJokeTextViewHolder.OnCollectJokeTextViewHolderListener listener) {
        this.mOnCollectJokeTextViewHolderListener = listener;
    }
}
