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
import com.maverick.bean.GifInfo;
import com.maverick.global.Tag;

import java.util.List;

/**
 * Created by ll on 2017/5/22.
 */
public class JokeItemFragmentAdapter extends RecyclerView.Adapter {

    public static final int JOKE_TEXT = 1;
    public static final int JOKE_IMG = 2;
    public static final int JOKE_GIF = 3;

    private List<GifInfo> mList;

    private Context mContext;

    public JokeItemFragmentAdapter(Context context) {
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        JokeTextViewHolder jokeTextViewHolder;
        final GifInfo gifInfo = mList.get(position);
        switch (holder.getItemViewType()) {
            case JOKE_IMG:
            case JOKE_GIF:
                JokeImgViewHolder mMyViewHolder = (JokeImgViewHolder) holder;
                jokeTextViewHolder = mMyViewHolder;
                if (mOnItemClickListener != null) {
                    mMyViewHolder.img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickListener.onItemClick(view, position, gifInfo);
                        }
                    });
                }
                break;
            default:
                jokeTextViewHolder = (JokeTextViewHolder) holder;
                break;
        }

        jokeTextViewHolder.bindData(mContext, gifInfo);
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
        this.mList = list;
    }

    public List<GifInfo> getData() {
        return mList;
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, GifInfo gifInfo);
    }
}
