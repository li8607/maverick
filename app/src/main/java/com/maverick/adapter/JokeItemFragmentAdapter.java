package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.holder.JokeImgViewHolder;
import com.maverick.adapter.holder.JokeTextViewHolder;
import com.maverick.base.ThemeAdapter;
import com.maverick.base.ThemedViewHolder;
import com.maverick.bean.GifInfo;
import com.maverick.global.Tag;

import java.util.List;

/**
 * Created by ll on 2017/5/22.
 */
public class JokeItemFragmentAdapter<T extends ThemedViewHolder> extends ThemeAdapter {

    public static final int JOKE_TEXT = 1;
    public static final int JOKE_IMG = 2;
    public static final int JOKE_GIF = 3;

    private List<GifInfo> mList;

    private Context mContext;

    public JokeItemFragmentAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;

        switch (viewType) {
            case JOKE_TEXT:
                holder = new JokeTextViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_joke_text, parent, false));
                break;
            case JOKE_IMG:
            case JOKE_GIF:
                holder = new JokeImgViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_joke_img, parent, false));
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
        JokeTextViewHolder mMyViewHolder = (JokeTextViewHolder) holder;
        mMyViewHolder.bindData(mContext, mList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        String type = mList.get(position).getType();
        if (TextUtils.equals(type, Tag.JOKE_TEXT)) {
            return JOKE_TEXT;
        } else if (TextUtils.equals(type, Tag.JOKE_IMG)) {
            return JOKE_IMG;
        } else if (TextUtils.equals(type, Tag.JOKE_GIF)) {
            return JOKE_GIF;
        }
        return JOKE_TEXT;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<GifInfo> list) {
        if (list != null) {
            this.mList = list;
        }
    }

    public void setMoreData(List<GifInfo> list) {
        if (list != null) {
            mList.addAll(list);
        }
    }

    public List<GifInfo> getData() {
        return mList;
    }
}
