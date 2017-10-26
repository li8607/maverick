package com.maverick.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.maverick.R;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.PearVideoTabInfo;
import com.maverick.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/10/26.
 */

public class PearItemFragment extends BaseFragment2 {

    private static final String ARGUMENT = "ARGUMENT";

    private TabLayout tab_layout;
    private ViewPager viewpager;

    public static PearItemFragment newInstance(PearVideoTabInfo info) {
        PearItemFragment fragment = new PearItemFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, info);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_pear;
    }

    @Override
    protected void onInitView(View view) {
        tab_layout = findView(R.id.tab_layout);
        viewpager = findView(R.id.viewpager);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

    }
}
