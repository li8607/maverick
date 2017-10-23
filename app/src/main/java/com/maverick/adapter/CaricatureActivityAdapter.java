package com.maverick.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.maverick.util.GlideUtil;

import java.util.List;

/**
 * Created by limingfei on 2017/10/23.
 */
public class CaricatureActivityAdapter extends PagerAdapter {

    private List<String> mList;

    public CaricatureActivityAdapter(List<String> list) {
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideUtil.loadImage(container.getContext(), mList.get(position), imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
