package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.holder.CollectImageViewHolder;
import com.maverick.adapter.holder.CollectTextViewHolder;
import com.maverick.adapter.holder.CollectVideoViewHolder;
import com.maverick.type.CollectType;

import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/10/5.
 */
public class CollectItemFragmentAdapter extends RecyclerView.Adapter {

    private static final int TYPE_TEXT = 1;
    private static final int TYPE_IMG = 2;
    private static final int TYPE_VIDEO = 3;

    private List<Collect> mList;

    private Context mContext;

    private boolean editState;

    public CollectItemFragmentAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;

        switch (viewType) {
            case TYPE_TEXT:
                CollectTextViewHolder collectTextViewHolder = new CollectTextViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_collect_text, parent, false));
                collectTextViewHolder.setOnCollectJokeTextViewHolderListener(mOnCollectJokeTextViewHolderListener);
                holder = collectTextViewHolder;
                break;
            case TYPE_IMG:
                CollectImageViewHolder collectImageViewHolder = new CollectImageViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_collect_image, parent, false));
                collectImageViewHolder.setOnCollectJokeTextViewHolderListener(mOnCollectJokeTextViewHolderListener);
                holder = collectImageViewHolder;
                break;
            case TYPE_VIDEO:
                CollectVideoViewHolder collectVideoViewHolder = new CollectVideoViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_collect_video, parent, false));
                collectVideoViewHolder.setOnCollectJokeTextViewHolderListener(mOnCollectJokeTextViewHolderListener);
                holder = collectVideoViewHolder;
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
        if (holder instanceof CollectTextViewHolder) {
            CollectTextViewHolder collectTextViewHolder = (CollectTextViewHolder) holder;
            collectTextViewHolder.bindData(mContext, mList.get(position), editState);
        }
    }

    @Override
    public int getItemViewType(int position) {

        Collect collect = mList.get(position);

        String type = collect.getCollectType();
        String itemType = collect.getCollectItemType();

        if (TextUtils.equals(type, CollectType.CARICATURE) && !TextUtils.isEmpty(collect.getCollectImage())) {
            return TYPE_IMG;
        } else if (TextUtils.equals(type, CollectType.SISTER)) {
            if (TextUtils.equals(itemType, CollectType.SISTER_IMAGE) && !TextUtils.isEmpty(collect.getCollectImage())) {
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
        } else if (TextUtils.equals(type, CollectType.SINA)) {
            if (!TextUtils.isEmpty(collect.getCollectImage())) {
                //图片
                return TYPE_IMG;
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

    private CollectTextViewHolder.OnCollectJokeTextViewHolderListener mOnCollectJokeTextViewHolderListener = new CollectTextViewHolder.OnCollectJokeTextViewHolderListener() {
        @Override
        public void onItemClick(int position, Collect collect) {
            if (editState) {
                return;
            }
        }
    };

    public void setOnCollectJokeTextViewHolderListener(CollectTextViewHolder.OnCollectJokeTextViewHolderListener listener) {
        this.mOnCollectJokeTextViewHolderListener = listener;
    }
}
