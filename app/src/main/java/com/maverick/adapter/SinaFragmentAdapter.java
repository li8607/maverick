package com.maverick.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;

import com.maverick.bean.SinaTabInfo;
import com.maverick.fragment.SinaFragment;
import com.maverick.fragment.SinaItemFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public class SinaFragmentAdapter extends FragmentStatePagerAdapter {

    private List<SinaTabInfo> mList;

    public SinaFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        SinaTabInfo info = mList.get(position);

        if (TextUtils.isEmpty(info.getSpace())) {
            return SinaFragment.newInstance(info);
        } else {
            return SinaItemFragment.newInstance(info);
        }
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<SinaTabInfo> list) {
        mList = list;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getTitle();
    }
}
