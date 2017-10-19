package com.maverick.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.maverick.bean.SisterTabInfo;
import com.maverick.fragment.SisterItemItemFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public class SisterFragmentAdapter extends FragmentStatePagerAdapter {

    private List<SisterTabInfo> mList;

    public SisterFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        SisterTabInfo info = mList.get(position);
        return SisterItemItemFragment.newInstance(info);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<SisterTabInfo> list) {
        mList = list;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getTitle();
    }
}
