package com.maverick.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.bean.GifInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ll on 2017/5/22.
 */
public class TextAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GifInfo> mList = new ArrayList<>();

    private Context mContext;

    public TextAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_text_item, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder mMyViewHolder = (MyViewHolder) holder;
        mMyViewHolder.setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<GifInfo> list) {
        this.mList = list;
    }

    public void setMoreData(List<GifInfo> list) {
        if (mList != null) {
            mList.addAll(list);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTitle;
        private GifInfo gifInfo;

        public MyViewHolder(final View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailActivity.launch((Activity) mContext, itemView, gifInfo);
                }
            });
        }

        public void setData(GifInfo gifInfo) {
            this.gifInfo = gifInfo;
            String text = gifInfo.text;
            Log.e("lmf", "text = " + text);
            if (!TextUtils.isEmpty(text)) {
                mTitle.setText(text);
            }
        }
    }
}
