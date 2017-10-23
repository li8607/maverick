package com.maverick.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.maverick.bean.CollectTabInfo;
import com.maverick.fragment.BaseEditFragment;
import com.maverick.fragment.CollectFragment;
import com.maverick.fragment.CollectItemFragment;
import com.maverick.type.FragmentType;

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
            case FragmentType.COLLECT:
                CollectFragment collectFragment = CollectFragment.newInstance(mList.get(position));
                collectFragment.setOnCollectFragmentListener(mOnCollectFragmentListener);
                collectFragment.setOnBaseEditFragmentListener(mOnBaseEditFragmentListener);
                fragment = collectFragment;
                break;
            case FragmentType.COLLECT_CARICATURE:
            case FragmentType.COLLECT_SISTER:
            case FragmentType.COLLECT_SINA:
                CollectItemFragment collectItemFragment = CollectItemFragment.newInstance(mList.get(position));
                collectItemFragment.setOnBaseEditFragmentListener(mOnBaseEditFragmentListener);
                fragment = collectItemFragment;
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

    private BaseEditFragment.OnBaseEditFragmentListener mOnBaseEditFragmentListener;

    public void setOnBaseEditFragmentListener(BaseEditFragment.OnBaseEditFragmentListener listener) {
        this.mOnBaseEditFragmentListener = listener;
    }
}
