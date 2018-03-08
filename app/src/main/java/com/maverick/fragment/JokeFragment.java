package com.maverick.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.maverick.R;
import com.maverick.adapter.JokeFragmentAdapter;
import com.maverick.base.BaseFragment;
import com.maverick.bean.JokeTabInfo;
import com.maverick.global.Tag;
import com.maverick.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public class JokeFragment extends BaseFragment {

    private TabLayout tab_layout;
    private ViewPager viewpager;
    private JokeFragmentAdapter mJokeFragmentAdapter;

    public static JokeFragment newInstance() {
        JokeFragment fragment = new JokeFragment();
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

        mJokeFragmentAdapter = new JokeFragmentAdapter(getChildFragmentManager());
        viewpager.setAdapter(mJokeFragmentAdapter);
        tab_layout.setupWithViewPager(viewpager);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        List<JokeTabInfo> list = new ArrayList<>();
        list.add(getJokeTabInfo("文本笑话", Tag.JOKE_TAB_TEXT));
        list.add(getJokeTabInfo("图文笑话", Tag.JOKE_TAB_IMG));
        list.add(getJokeTabInfo("动图笑话", Tag.JOKE_TAB_GIF));

        mJokeFragmentAdapter.setData(list);
        mJokeFragmentAdapter.notifyDataSetChanged();
    }

    private JokeTabInfo getJokeTabInfo(String title, int type) {
        JokeTabInfo jokeTabInfo = new JokeTabInfo();
        jokeTabInfo.setTitle(title);
        jokeTabInfo.setType(type);
        return jokeTabInfo;
    }

    public void refreshUI() {

        TypedValue colorPrimary = new TypedValue();
        TypedValue colorTabLayoutIndicator = new TypedValue();
        TypedValue textColorTabLayoutSelected = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, colorPrimary, true);
        theme.resolveAttribute(R.attr.colorTabLayoutIndicator, colorTabLayoutIndicator, true);
        theme.resolveAttribute(R.attr.textColorTabLayoutSelected, textColorTabLayoutSelected, true);

        tab_layout.setBackgroundResource(colorPrimary.resourceId);
        tab_layout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), colorTabLayoutIndicator.resourceId));
        tab_layout.setTabTextColors(ContextCompat.getColor(getContext(), R.color.textColorTabLayout), ContextCompat.getColor(getContext(), textColorTabLayoutSelected.resourceId));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mJokeFragmentAdapter != null) {
            JokeItemFragment jokeItemFragment = (JokeItemFragment) mJokeFragmentAdapter.instantiateItem(viewpager, viewpager.getCurrentItem());
            if (jokeItemFragment != null) {
                jokeItemFragment.setUserVisibleHintSuper(isVisibleToUser);
            }
        }
    }
}
