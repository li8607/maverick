package com.maverick.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.maverick.R;
import com.maverick.bean.PearVideoInfo;
import com.maverick.util.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class PearGalleryViewHolderAdapter extends PagerAdapter implements View.OnClickListener {

    private Context mContext;
    private List<PearVideoInfo> mList;

    public PearGalleryViewHolderAdapter(Context context, List<PearVideoInfo> list) {
        this.mContext = context;
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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pear_gallery_item, container, false);
        view.setTag(position);
        view.setOnClickListener(this);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        String pic = mList.get(position).getPic();
        GlideUtil.loadImage(mContext, pic, image);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object != null && object instanceof View) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnListener != null && v.getTag() != null && v.getTag() instanceof Integer) {
            mOnListener.onItemClick((int) v.getTag());
        }
    }

    private OnListener mOnListener;

    public void setOnListener(OnListener listener) {
        this.mOnListener = listener;
    }

    public interface OnListener {
        void onItemClick(int position);
    }
}
