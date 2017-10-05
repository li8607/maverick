package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.holder.CollectJokeImgViewHolder;
import com.maverick.adapter.holder.CollectJokeTextViewHolder;

import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/10/5.
 */
public class CollectItemJokeFragmentAdapter extends RecyclerView.Adapter {

    private static final int TYPE_TEXT = 1;
    private static final int TYPE_IMG = 2;
    private static final int TYPE_GIF = 3;

    private List<Collect> mList;

    private Context mContext;

    private boolean editState;

    public CollectItemJokeFragmentAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;

        switch (viewType) {
            case TYPE_TEXT:
                CollectJokeTextViewHolder collectJokeTextViewHolder = new CollectJokeTextViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_collect_joke_text, parent, false));
                collectJokeTextViewHolder.setOnCollectJokeTextViewHolderListener(mOnCollectJokeTextViewHolderListener);
                holder = collectJokeTextViewHolder;
                break;
            case TYPE_IMG:
            case TYPE_GIF:
                CollectJokeImgViewHolder collectJokeImgViewHolder = new CollectJokeImgViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_collect_joke_img, parent, false));
                collectJokeImgViewHolder.setOnCollectJokeTextViewHolderListener(mOnCollectJokeTextViewHolderListener);
                holder = collectJokeImgViewHolder;
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

        String type = collect.getCollectItemType();

        if (TextUtils.equals(type, "1")) {
            //文本
            return TYPE_TEXT;
        } else if (TextUtils.equals(type, "2")) {
            //图文
            return TYPE_IMG;
        } else if (TextUtils.equals(type, "3")) {
            //动图
            return TYPE_GIF;
        }

        return super.getItemViewType(position);
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
