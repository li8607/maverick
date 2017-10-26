package com.maverick.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.PearVideoTabInfo;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.PearItemFragmentPresenter;
import com.maverick.presenter.implView.IPearItemFragmentView;

/**
 * Created by Administrator on 2017/10/26.
 */

public class PearItemFragment extends BaseFragment2 implements IPearItemFragmentView {

    private static final String ARGUMENT = "ARGUMENT";

    private TabLayout tab_layout;
    private ViewPager viewpager;
    private PearItemFragmentPresenter mPresenter;
    private TextView text;

    public static PearItemFragment newInstance(PearVideoTabInfo info) {
        PearItemFragment fragment = new PearItemFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, info);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new PearItemFragmentPresenter(getContext(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_pear_item;
    }

    @Override
    protected void onInitView(View view) {

        text = findView(R.id.text);

    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        PearVideoTabInfo info = (PearVideoTabInfo) getArguments().getSerializable(ARGUMENT);
        text.setText(info.getName());
        mPresenter.loadData(info);
    }
}
