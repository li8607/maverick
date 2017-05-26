package com.maverick.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.bean.GifInfo;
import com.maverick.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ll on 2017/5/22.
 */
public class ImgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GifInfo> mList = new ArrayList<>();
    private Context mContext;

    public ImgAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_img_item, null);
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
        mList = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView mImageView;
        private final TextView mTitle;
        private GifInfo mGifInfo;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mImageView.setOnClickListener(this);
        }

        public void setData(GifInfo gifInfo) {
            mGifInfo = gifInfo;
            String img = gifInfo.img;
            String title = gifInfo.title;

            if (!TextUtils.isEmpty(title)) {
                mTitle.setText(title);
            }

            GlideUtil.loadImage(mContext, img, mImageView);
        }

        @Override
        public void onClick(View v) {
            DetailActivity.launch((Activity) mContext, mImageView, mGifInfo);
        }
    }
}
