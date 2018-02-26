package com.maverick.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.maverick.R;
import com.maverick.adapter.CaricatureFragmentAdapter;
import com.maverick.base.BaseFragment;
import com.maverick.bean.CaricatureTabInfo;
import com.maverick.global.Tag;
import com.maverick.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public class CaricatureFragment extends BaseFragment {

    private TabLayout tab_layout;
    private ViewPager viewpager;
    private CaricatureFragmentAdapter mAdapter;

    public static CaricatureFragment newInstance() {
        CaricatureFragment fragment = new CaricatureFragment();
        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_caricature;
    }

    @Override
    protected void onInitView(View view) {
        tab_layout = findView(R.id.tab_layout);
        viewpager = findView(R.id.viewpager);

        mAdapter = new CaricatureFragmentAdapter(getChildFragmentManager());
        viewpager.setAdapter(mAdapter);
        tab_layout.setupWithViewPager(viewpager);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        List<CaricatureTabInfo> list = new ArrayList<>();
//        list.add(getCaricatureTabInfo("恐怖", Tag.CARICATURE_TERROR));
        list.add(getCaricatureTabInfo("故事", Tag.CARICATURE_STORY));
        list.add(getCaricatureTabInfo("段子", Tag.CARICATURE_JOKES));
        list.add(getCaricatureTabInfo("冷知识", Tag.CARICATURE_TRIVIA));
        list.add(getCaricatureTabInfo("奇趣", Tag.CARICATURE_SINGULAR));
        list.add(getCaricatureTabInfo("电影", Tag.CARICATURE_MOVIE));
        list.add(getCaricatureTabInfo("搞笑", Tag.CARICATURE_FUNNY));
        list.add(getCaricatureTabInfo("萌宠", Tag.CARICATURE_PET));
        list.add(getCaricatureTabInfo("新奇", Tag.CARICATURE_NEW));
        list.add(getCaricatureTabInfo("环球", Tag.CARICATURE_GLOBAL));
        list.add(getCaricatureTabInfo("摄影", Tag.CARICATURE_SHOOT));
        list.add(getCaricatureTabInfo("玩艺", Tag.CARICATURE_PLAYTHING));
        list.add(getCaricatureTabInfo("插画", Tag.CARICATURE_INSET));
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    private CaricatureTabInfo getCaricatureTabInfo(String title, String type) {
        CaricatureTabInfo info = new CaricatureTabInfo();
        info.setTitle(title);
        info.setType(type);
        return info;
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
}
