package com.maverick.fragment;

import android.graphics.Rect;
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
import com.maverick.util.DensityUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

/**
 * Created by limingfei on 2017/9/26.
 */
public class BeautyFragment extends BaseFragment2 implements IBeautyFragmentView {

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private BeautyFragmentAdapter mBeautyFragmentAdapter;
    private BeautyFragmentPresenter mPresenter;
    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;

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
        pullLoadMoreRecyclerView = findView(R.id.recyclerView);
        pullLoadMoreRecyclerView.setHasMore(true);
        pullLoadMoreRecyclerView.setPullRefreshEnable(true);

        recyclerView = pullLoadMoreRecyclerView.getRecyclerView();
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        mBeautyFragmentAdapter = new BeautyFragmentAdapter(getContext());
        recyclerView.setAdapter(mBeautyFragmentAdapter);

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData();
            }

            @Override
            public void onLoadMore() {
                mPresenter.loadMoreData();
            }
        });

        pullLoadMoreRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                int position = parent.getChildAdapterPosition(view);

                if(position < 2) {
                    outRect.top = DensityUtil.dip2px(getActivity(), 10);
                }else {
                    outRect.top = DensityUtil.dip2px(getActivity(), 5);
                }

                outRect.bottom = DensityUtil.dip2px(getActivity(), 5);

                if (position % 2 == 0) {
                    outRect.left = DensityUtil.dip2px(getActivity(), 10);
                    outRect.right = DensityUtil.dip2px(getActivity(), 5);
                } else {
                    outRect.left = DensityUtil.dip2px(getActivity(), 5);
                    outRect.right = DensityUtil.dip2px(getActivity(), 10);
                }
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mPresenter.refreshData();
    }

    @Override
    public void onShowSuccessView(List<BeautyItemInfo> beautyItemInfos) {

        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        mBeautyFragmentAdapter.setData(beautyItemInfos);
        mBeautyFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowEmptyView() {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void onShowErrorView() {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void onLoadMoreSuccess(List<BeautyItemInfo> beautyInfo, boolean isHasMore) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(isHasMore);

        int positionStart = mBeautyFragmentAdapter.getItemCount();

        mBeautyFragmentAdapter.setMoreData(beautyInfo);
        mBeautyFragmentAdapter.notifyItemRangeInserted(positionStart, beautyInfo.size());
    }

    @Override
    public void onLoadMoreFail() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }
}
