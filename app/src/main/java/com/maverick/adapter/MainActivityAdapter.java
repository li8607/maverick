package com.maverick.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.maverick.bean.ButtonInfo;
import com.maverick.factory.FragmentFactory;

import java.util.List;

/**
 * Created by limingfei on 2018/2/11.
 */

public class MainActivityAdapter extends FragmentPagerAdapter {

    private List<ButtonInfo> mList;

    public MainActivityAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = FragmentFactory.getMainFragment(mList.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    public List<ButtonInfo> getList() {
        return mList;
    }

    public void setList(List<ButtonInfo> list) {
        mList = list;
    }
}
