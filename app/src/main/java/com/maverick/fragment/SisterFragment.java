package com.maverick.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.maverick.R;
import com.maverick.adapter.SisterFragmentAdapter;
import com.maverick.base.BaseFragment;
import com.maverick.bean.SisterTabInfo;
import com.maverick.global.Tag;
import com.maverick.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public class SisterFragment extends BaseFragment {

    private TabLayout tab_layout;
    private ViewPager viewpager;
    private SisterFragmentAdapter mSisterFragmentAdapter;

    public static SisterFragment newInstance() {
        SisterFragment fragment = new SisterFragment();
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

        mSisterFragmentAdapter = new SisterFragmentAdapter(getChildFragmentManager());
        viewpager.setAdapter(mSisterFragmentAdapter);
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
        mSisterFragmentAdapter.setData(list);
        mSisterFragmentAdapter.notifyDataSetChanged();
    }

    private SisterTabInfo getSisterTabInfo(String title, String type) {
        SisterTabInfo info = new SisterTabInfo();
        info.setTitle(title);
        info.setType(type);
        return info;
    }
}
