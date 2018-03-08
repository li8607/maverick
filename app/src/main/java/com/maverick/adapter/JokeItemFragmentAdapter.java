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

    private GifInfo mSpeechInfo;
    private boolean mSpeaking;

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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        JokeTextViewHolder jokeTextViewHolder;
        final GifInfo gifInfo = mList.get(position);
        switch (holder.getItemViewType()) {
            case JOKE_IMG:
            case JOKE_GIF:
                JokeImgViewHolder mMyViewHolder = (JokeImgViewHolder) holder;
                jokeTextViewHolder = mMyViewHolder;
                if (mOnItemChildClickListener != null) {
                    mMyViewHolder.img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemChildClickListener.onImageClick(view, position, gifInfo);
                        }
                    });
                }
                break;
            case JOKE_TEXT:
                jokeTextViewHolder = (JokeTextViewHolder) holder;
                jokeTextViewHolder.iv_play_joke.setSelected(gifInfo != null && gifInfo.equals(mSpeechInfo));
                jokeTextViewHolder.iv_play_joke.setImageResource(gifInfo != null && gifInfo.equals(mSpeechInfo) && mSpeaking ? R.drawable.ic_pause_black_24dp : R.drawable.ic_play_arrow_black_24dp);
                if (mSpeaking && jokeTextViewHolder.pb_loading.getVisibility() == View.VISIBLE) {
                    jokeTextViewHolder.pb_loading.setVisibility(View.INVISIBLE);
                }
                jokeTextViewHolder.pb_progress.setVisibility(gifInfo != null && gifInfo.equals(mSpeechInfo) ? View.VISIBLE : View.INVISIBLE);
                if (mOnItemChildClickListener != null) {
                    jokeTextViewHolder.fl_play_joke.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemChildClickListener.onPlayClick(holder, position, gifInfo);
                        }
                    });

                    jokeTextViewHolder.fl_stop_joke.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemChildClickListener.onStopClick(holder, position, gifInfo);
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

    private OnItemChildClickListener mOnItemChildClickListener;

    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        this.mOnItemChildClickListener = listener;
    }

    public void setSpeechData(GifInfo info) {
        this.mSpeechInfo = info;
    }

    public void setSpeaking(boolean speaking) {
        this.mSpeaking = speaking;
    }

    public interface OnItemChildClickListener {
        void onImageClick(View view, int position, GifInfo gifInfo);

        void onPlayClick(RecyclerView.ViewHolder viewHolder, int position, GifInfo gifInfo);

        void onStopClick(RecyclerView.ViewHolder viewHolder, int position, GifInfo gifInfo);
    }
}
