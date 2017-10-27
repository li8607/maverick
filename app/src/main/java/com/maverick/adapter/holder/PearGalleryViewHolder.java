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

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class PearGalleryViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

    private final ViewPager viewPager;

    public PearGalleryViewHolder(View itemView) {
        super(itemView);
        viewPager = (ViewPager) itemView.findViewById(R.id.viewpager);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        //设置预加载数量
        viewPager.setOffscreenPageLimit(2);
        //设置每页之间的左右间隔
        viewPager.setPageMargin(itemView.getResources().getDimensionPixelSize(R.dimen.x48));
        //将容器的触摸事件反馈给ViewPager
        itemView.setOnTouchListener(this);
    }

    public void bindData(Context context, List<PearVideoInfo> list) {
        PearGalleryViewHolderAdapter mAdapter = new PearGalleryViewHolderAdapter(context, list);
        viewPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return viewPager.dispatchTouchEvent(event);
    }
}
