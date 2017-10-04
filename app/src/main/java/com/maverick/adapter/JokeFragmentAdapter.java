package com.maverick.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.maverick.bean.JokeTabInfo;
import com.maverick.fragment.JokeItemFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public class JokeFragmentAdapter extends FragmentStatePagerAdapter {

    private List<JokeTabInfo> mList;

    public JokeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        JokeTabInfo jokeTabInfo = mList.get(position);
        return JokeItemFragment.newInstance(jokeTabInfo);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<JokeTabInfo> list) {
        mList = list;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getTitle();
    }
}
