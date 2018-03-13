package com.maverick.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.maverick.R;
import com.maverick.adapter.CollectFragmentAdapter;
import com.maverick.base.BaseFragment;
import com.maverick.bean.CollectTabInfo;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.CollectFragmentPresenter;
import com.maverick.presenter.implView.ICollectFragmentView;
import com.maverick.transformer.DepthPageTransformer;

import java.util.List;

/**
 * Created by limingfei on 2017/9/29.
 */
public class CollectFragment extends BaseFragment implements ICollectFragmentView {

    private static final String KEY_INFOS = "CollectTabInfos";

    private TabLayout tab_layout;
    private ViewPager viewpager;
    private CollectFragmentAdapter mCollectFragmentAdapter;
    private CollectFragmentPresenter mPPresenter;

    public static CollectFragment newInstance(CollectTabInfo info) {

        CollectFragment fragment = new CollectFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_INFOS, info);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static CollectFragment newInstance() {
        CollectFragment fragment = new CollectFragment();
        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPPresenter = new CollectFragmentPresenter(getContext(), this);
        return mPPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void onInitView(View view) {
        tab_layout = findView(R.id.tab_layout);
        viewpager = findView(R.id.viewpager);
        viewpager.setPageTransformer(true, new DepthPageTransformer());
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mCollectFragmentAdapter = new CollectFragmentAdapter(getChildFragmentManager());
        viewpager.setAdapter(mCollectFragmentAdapter);
        tab_layout.setupWithViewPager(viewpager);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        CollectTabInfo info = (CollectTabInfo) getArguments().getSerializable(KEY_INFOS);
        if (info == null) {
            mPPresenter.loadAllTabData();
        } else {
            mPPresenter.loadAllTabData();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPPresenter.loadAllTabData();
            for (int i = 0; i < getChildFragmentManager().getFragments().size(); i++) {
                getChildFragmentManager().getFragments().get(i).onHiddenChanged(hidden);
            }
        }
    }

    @Override
    public void onShowSuccessView(List<CollectTabInfo> list) {
        mCollectFragmentAdapter.setData(list);
        mCollectFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowEmptyView() {
        Toast.makeText(getContext(), "暂无数据", Toast.LENGTH_SHORT).show();
    }
}
