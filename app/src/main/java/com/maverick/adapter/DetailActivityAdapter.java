package com.maverick.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.maverick.bean.BigImgInfo;
import com.maverick.fragment.ImageFragment;

import java.util.List;

/**
 * Created by limingfei on 2018/2/11.
 */

public class DetailActivityAdapter extends FragmentStatePagerAdapter {

    private List<BigImgInfo> mList;

    public DetailActivityAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(mList.get(position));
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    public List<BigImgInfo> getList() {
        return mList;
    }

    public void setList(List<BigImgInfo> list) {
        mList = list;
    }
}
