package com.maverick.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.maverick.bean.CaricatureTabInfo;
import com.maverick.fragment.CaricatureItemFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public class CaricatureFragmentAdapter extends FragmentStatePagerAdapter {

    private List<CaricatureTabInfo> mList;

    public CaricatureFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        CaricatureTabInfo info = mList.get(position);
        return CaricatureItemFragment.newInstance(info);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<CaricatureTabInfo> list) {
        mList = list;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getTitle();
    }
}
