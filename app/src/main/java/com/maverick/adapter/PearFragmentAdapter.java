package com.maverick.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.maverick.bean.PearVideoTabInfo;
import com.maverick.fragment.PearItemFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public class PearFragmentAdapter extends FragmentStatePagerAdapter {

    private List<PearVideoTabInfo> mList;

    public PearFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        PearVideoTabInfo info = mList.get(position);

        PearItemFragment fragment = PearItemFragment.newInstance(info);
        return fragment;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<PearVideoTabInfo> list) {
        mList = list;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getName();
    }
}
