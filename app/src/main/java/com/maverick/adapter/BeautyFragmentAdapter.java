package com.maverick.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.bean.BeautyItemInfo;
import com.maverick.bean.BigImgInfo;
import com.maverick.hepler.BeanHelper;
import com.maverick.model.CollectModel;
import com.maverick.util.GlideUtil;
import com.maverick.weight.RatioImageView;

import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by Administrator on 2017/9/26.
 */
public class BeautyFragmentAdapter extends RecyclerView.Adapter {

    private List<BeautyItemInfo> mList;
    private Context mContext;

    public BeautyFragmentAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_beauty, parent, false);
        return new BeautyHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        BeautyHolder beautyHolder = (BeautyHolder) holder;
        beautyHolder.bindData(mList.get(position));
        if (mOnItemClickListener != null) {
            beautyHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<BeautyItemInfo> beautyItemInfos) {
        this.mList = beautyItemInfos;
    }

    public void setMoreData(List<BeautyItemInfo> beautyItemInfos) {
        if (mList != null) {
            mList.addAll(beautyItemInfos);
        }
    }

    public List<BeautyItemInfo> getData() {
        return mList;
    }

    public class BeautyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView image;
        private final TextView title;
        private BeautyItemInfo mBeautyItemInfo;
        private final View collect;

        public BeautyHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);

            collect = itemView.findViewById(R.id.collect);

            collect.setOnClickListener(this);
        }

        public void bindData(BeautyItemInfo beautyItemInfo) {
            this.mBeautyItemInfo = beautyItemInfo;
            GlideUtil.loadImage(mContext, beautyItemInfo.getUrl(), image);
            this.collect.setSelected(beautyItemInfo.isCheck());
        }

        @Override
        public void onClick(View v) {
            if (mBeautyItemInfo == null) {
                return;
            }

            switch (v.getId()) {
                case R.id.collect:

                    if (mBeautyItemInfo == null) {
                        return;
                    }

                    Collect collect = BeanHelper.getCollect(mBeautyItemInfo);
                    if (mBeautyItemInfo.isCheck()) {
                        this.collect.setSelected(false);
                        mBeautyItemInfo.setCheck(false);
                        CollectModel.newInstance().deleteCollectDB(collect);
                    } else {
                        this.collect.setSelected(true);
                        mBeautyItemInfo.setCheck(true);
                        CollectModel.newInstance().insertCollectDB(collect);
                    }
                    break;
//                case R.id.image:
//                    BigImgInfo bigImgInfo = new BigImgInfo();
//                    bigImgInfo.setImg(mBeautyItemInfo.getUrl());
//                    DetailActivity.launch((Activity) mContext, image, bigImgInfo);
//                    break;
            }
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView.ViewHolder viewHolder, int position);
    }
}
