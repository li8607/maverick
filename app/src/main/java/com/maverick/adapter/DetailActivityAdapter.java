package com.maverick.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.maverick.bean.BigImgInfo;
import com.maverick.fragment.ImageFragment;

import java.util.List;

/**
 * Created by limingfei on 2018/2/11.
 */

public class DetailActivityAdapter extends FragmentPagerAdapter {

    private List<BigImgInfo> mList;
    private BigImgInfo mBigImgInfo;

    public DetailActivityAdapter(FragmentManager fm, BigImgInfo info) {
        super(fm);
        this.mBigImgInfo = info;
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(mList.get(position), mBigImgInfo == null ? position == 0 ? mList.get(position).getImg() : "" : mBigImgInfo.getImg());
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
