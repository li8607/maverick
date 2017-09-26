package com.maverick.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.maverick.R;
import com.maverick.adapter.BeautyFragmentAdapter;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.BeautyItemInfo;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.BeautyFragmentPresenter;
import com.maverick.presenter.implView.IBeautyFragmentView;

import java.util.List;

/**
 * Created by limingfei on 2017/9/26.
 */
public class BeautyFragment extends BaseFragment2 implements IBeautyFragmentView {

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private BeautyFragmentAdapter mBeautyFragmentAdapter;
    private BeautyFragmentPresenter mPresenter;

    public static BeautyFragment newInstance() {
        BeautyFragment fragment = new BeautyFragment();
        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new BeautyFragmentPresenter(getContext(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_beauty;
    }

    @Override
    protected void onInitView(View view) {
        recyclerView = findView(R.id.recyclerView);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        mBeautyFragmentAdapter = new BeautyFragmentAdapter(getContext());
        recyclerView.setAdapter(mBeautyFragmentAdapter);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mPresenter.refreshData();
    }

    @Override
    public void onShowSuccessView(List<BeautyItemInfo> beautyItemInfos) {

        if(getActivity() == null || getActivity().isFinishing()) {
            return;
        }

        mBeautyFragmentAdapter.setData(beautyItemInfos);
        mBeautyFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowEmptyView() {
        if(getActivity() == null || getActivity().isFinishing()) {
            return;
        }
    }

    @Override
    public void onShowErrorView() {
        if(getActivity() == null || getActivity().isFinishing()) {
            return;
        }
    }
}
