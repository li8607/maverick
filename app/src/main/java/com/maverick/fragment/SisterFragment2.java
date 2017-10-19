package com.maverick.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.maverick.R;
import com.maverick.adapter.SisterFragmentAdapter2;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.SisterTabInfo;
import com.maverick.global.Tag;
import com.maverick.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public class SisterFragment2 extends BaseFragment2 {

    private TabLayout tab_layout;
    private ViewPager viewpager;
    private SisterFragmentAdapter2 mSisterFragmentAdapter2;

    public static SisterFragment2 newInstance() {
        SisterFragment2 fragment = new SisterFragment2();
        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_joke;
    }

    @Override
    protected void onInitView(View view) {
        tab_layout = findView(R.id.tab_layout);
        viewpager = findView(R.id.viewpager);

        mSisterFragmentAdapter2 = new SisterFragmentAdapter2(getChildFragmentManager());
        viewpager.setAdapter(mSisterFragmentAdapter2);
        tab_layout.setupWithViewPager(viewpager);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        List<SisterTabInfo> list = new ArrayList<>();
        list.add(getSisterTabInfo("推荐", ""));
        list.add(getSisterTabInfo("图片", Tag.SISTER_IMAGE));
        list.add(getSisterTabInfo("视频", Tag.SISTER_VIDEO));
        list.add(getSisterTabInfo("段子", Tag.SISTER_TEXT));
        list.add(getSisterTabInfo("声音", Tag.SISTER_AUDIO));
        mSisterFragmentAdapter2.setData(list);
        mSisterFragmentAdapter2.notifyDataSetChanged();
    }

    private SisterTabInfo getSisterTabInfo(String title, String type) {
        SisterTabInfo info = new SisterTabInfo();
        info.setTitle(title);
        info.setType(type);
        return info;
    }
}
