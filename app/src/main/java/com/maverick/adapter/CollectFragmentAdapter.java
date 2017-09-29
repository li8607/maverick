package com.maverick.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.maverick.bean.CollectTabInfo;
import com.maverick.fragment.CollectItemBeautyFragment;
import com.maverick.fragment.CollectItemFragment;
import com.maverick.fragment.CollectFragment;

import java.util.List;

/**
 * Created by limingfei on 2017/9/29.
 */
public class CollectFragmentAdapter extends FragmentStatePagerAdapter {

    private List<CollectTabInfo> mList;

    public CollectFragmentAdapter(FragmentManager fm, List<CollectTabInfo> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment;
        int type = mList.get(position).getType();
        switch (type) {
            case 1:
                CollectFragment collectFragment = CollectFragment.newInstance(mList.get(position).getItemList());
                collectFragment.setOnCollectFragmentListener(mOnCollectFragmentListener);
                fragment = collectFragment;
                break;
            case 2:
                CollectItemBeautyFragment collectItemBeautyFragment = CollectItemBeautyFragment.newInstance(mList.get(position));
                collectItemBeautyFragment.setOnCollectItemFragmentListener(mOnCollectItemFragmentListener);
                fragment = collectItemBeautyFragment;
                break;
            case 3:
                fragment = CollectItemFragment.newInstance(mList.get(position));
                break;
            case 4:
                fragment = CollectItemFragment.newInstance(mList.get(position));
                break;
            case 5:
                fragment = CollectItemFragment.newInstance(mList.get(position));
                break;
            default:
                fragment = new Fragment();
                break;
        }
        return fragment;
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

    private CollectItemFragment.OnCollectItemFragmentListener mOnCollectItemFragmentListener;

    public void setOnCollectItemFragmentListener(CollectItemFragment.OnCollectItemFragmentListener listener) {
        this.mOnCollectItemFragmentListener = listener;
    }
}
