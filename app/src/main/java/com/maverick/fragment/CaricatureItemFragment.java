package com.maverick.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.maverick.R;
import com.maverick.adapter.CaricatureItemFragmentAdapter;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.CaricatureInfo;
import com.maverick.bean.CaricatureTabInfo;
import com.maverick.global.Tag;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.CaricatureItemFragmentPresenter;
import com.maverick.presenter.implView.ICaricatureItemFragmentView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

/**
 * Created by Administrator on 2017/9/30.
 */
public class CaricatureItemFragment extends BaseFragment2 implements ICaricatureItemFragmentView {

    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private CaricatureItemFragmentAdapter mAdapter;
    private CaricatureItemFragmentPresenter mPresenter;
    private CaricatureTabInfo mInfo;

    private ViewGroup root;

    public static CaricatureItemFragment newInstance(CaricatureTabInfo info) {
        CaricatureItemFragment fragment = new CaricatureItemFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag.KEY_INFO, info);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new CaricatureItemFragmentPresenter(getContext(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_caricature_item;
    }

    @Override
    protected void onInitView(View view) {

        root = findView(R.id.root);

        pullLoadMoreRecyclerView = findView(R.id.recyclerView);
        RecyclerView mRecyclerView = pullLoadMoreRecyclerView.getRecyclerView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new CaricatureItemFragmentAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData(mInfo);
            }

            @Override
            public void onLoadMore() {
                mPresenter.loadMoreData();
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mInfo = (CaricatureTabInfo) getArguments().getSerializable(Tag.KEY_INFO);
        pullLoadMoreRecyclerView.setRefreshing(true);
        pullLoadMoreRecyclerView.refresh();
    }

    @Override
    public void onShowSuccessView(List<CaricatureInfo> list, boolean isHasMore) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(isHasMore);
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowEmptyView() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();

        View view = View.inflate(root.getContext(), R.layout.view_empty, null);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        root.addView(view, layoutParams);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.removeView(v);
                pullLoadMoreRecyclerView.setRefreshing(true);
                pullLoadMoreRecyclerView.refresh();
            }
        });
    }

    @Override
    public void onShowErrorView() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();

        View view = View.inflate(root.getContext(), R.layout.view_error, null);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;

        root.addView(view, layoutParams);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.removeView(v);
                pullLoadMoreRecyclerView.setRefreshing(true);
                pullLoadMoreRecyclerView.refresh();
            }
        });
    }

    @Override
    public void onLoadMoreSuccess(List<CaricatureInfo> list, boolean isHasMore) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(isHasMore);
        int positionStart = mAdapter.getItemCount();
        mAdapter.setMoreData(list);
        mAdapter.notifyItemRangeInserted(positionStart, list.size());
    }

    @Override
    public void onLoadMoreFail() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }
}
