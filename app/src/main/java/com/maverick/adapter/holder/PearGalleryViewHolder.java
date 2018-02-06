package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.maverick.R;
import com.maverick.adapter.PearGalleryViewHolderAdapter;
import com.maverick.bean.PearVideoInfo;
import com.maverick.transformer.ZoomOutPageTransformer;
import com.maverick.util.DensityUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class PearGalleryViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, PearGalleryViewHolderAdapter.OnListener {

    private final ViewPager viewPager;
    private List<PearVideoInfo> mList;

    public PearGalleryViewHolder(View itemView) {
        super(itemView);
        viewPager = (ViewPager) itemView.findViewById(R.id.viewpager);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        //设置预加载数量
        viewPager.setOffscreenPageLimit(2);
        //设置每页之间的左右间隔
        viewPager.setPageMargin(DensityUtil.dip2px(itemView.getContext(), 60));
        //将容器的触摸事件反馈给ViewPager
        itemView.setOnTouchListener(this);
    }

    public void bindData(Context context, List<PearVideoInfo> list) {
        this.mList = list;
        PearGalleryViewHolderAdapter mAdapter = new PearGalleryViewHolderAdapter(context, list);
        mAdapter.setOnListener(this);
        viewPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return viewPager.dispatchTouchEvent(event);
    }

    private PearGalleryViewHolder.OnListener mOnListener;

    public void setOnListener(PearGalleryViewHolder.OnListener listener) {
        this.mOnListener = listener;
    }

    @Override
    public void onItemClick(int position) {
        if (mList != null && position >= 0 && mList.size() > position) {
            PearVideoInfo info = mList.get(position);
            if (mOnListener != null) {
                mOnListener.onItemClick(info);
            }
        }
    }

    public interface OnListener {
        void onItemClick(PearVideoInfo info);
    }
}
