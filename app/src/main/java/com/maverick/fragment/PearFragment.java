package com.maverick.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.maverick.R;
import com.maverick.adapter.PearFragmentAdapter;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.PearVideoTabInfo;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.PearFragmentPresenter;
import com.maverick.presenter.implView.IPearFragmentView;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public class PearFragment extends BaseFragment2 implements IPearFragmentView {

    private TabLayout tab_layout;
    private ViewPager viewpager;
    private PearFragmentAdapter mAdapter;
    private PearFragmentPresenter mPresenter;
    private ViewGroup root;

    public static PearFragment newInstance() {
        PearFragment fragment = new PearFragment();
        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new PearFragmentPresenter(getContext(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_pear;
    }

    @Override
    protected void onInitView(View view) {
        root = findView(R.id.root);

        tab_layout = findView(R.id.tab_layout);
        viewpager = findView(R.id.viewpager);

        mAdapter = new PearFragmentAdapter(getChildFragmentManager());
        viewpager.setAdapter(mAdapter);
        tab_layout.setupWithViewPager(viewpager);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mPresenter.loadData();
    }

    @Override
    public void onShowSuccessView(List<PearVideoTabInfo> list) {
        tab_layout.setVisibility(View.VISIBLE);
        viewpager.setVisibility(View.VISIBLE);
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowEmptyView() {
        View view = View.inflate(root.getContext(), R.layout.view_error, null);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;

        root.addView(view, layoutParams);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.removeView(v);
            }
        });
    }

    @Override
    public void onShowErrorView() {
        View view = View.inflate(root.getContext(), R.layout.view_error, null);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;

        root.addView(view, layoutParams);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.removeView(v);
            }
        });
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

        for (int i = 0; i < mAdapter.getCount(); i++) {
            Object object = mAdapter.instantiateItem(viewpager, i);

            if (object == null) {
                continue;
            }

            BaseFragment2 baseFragment2 = (BaseFragment2) object;
            if (baseFragment2.isAdded()) {
                baseFragment2.refreshUI();
            }
        }
    }
}
