package com.maverick.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.maverick.bean.CollectTabInfo;
import com.maverick.fragment.BaseEditFragment;
import com.maverick.fragment.CollectFragment;
import com.maverick.fragment.CollectItemFragment;
import com.maverick.global.Tag;
import com.maverick.type.CollectType;
import com.maverick.type.FragmentType;

import java.util.List;

/**
 * Created by limingfei on 2017/9/29.
 */
public class CollectFragmentAdapter extends FragmentStatePagerAdapter {

    private List<CollectTabInfo> mList;

    public CollectFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        CollectItemFragment collectItemFragment = CollectItemFragment.newInstance(mList.get(position));
        collectItemFragment.setOnBaseEditFragmentListener(mOnBaseEditFragmentListener);
        return collectItemFragment;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        int index = mList.indexOf(((Fragment) object).getArguments().getSerializable(Tag.KEY_INFO));
        if (index == -1) {
            return POSITION_NONE;
        } else {
            return index;
        }
    }

    public void setData(List<CollectTabInfo> list) {
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getTitle();
    }

    private CollectFragment.OnCollectFragmentListener mOnCollectFragmentListener;

    public void setOnCollectFragmentListener(CollectFragment.OnCollectFragmentListener listener) {
        this.mOnCollectFragmentListener = listener;
    }

    private BaseEditFragment.OnBaseEditFragmentListener mOnBaseEditFragmentListener;

    public void setOnBaseEditFragmentListener(BaseEditFragment.OnBaseEditFragmentListener listener) {
        this.mOnBaseEditFragmentListener = listener;
    }
}
