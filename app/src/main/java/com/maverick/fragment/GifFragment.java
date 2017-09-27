package com.maverick.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.maverick.R;
import com.maverick.adapter.GifAdapter;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.GifInfo;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.GifFragmentPresenter;
import com.maverick.presenter.implView.GifFragmentView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

/**
 * Created by ll on 2017/5/18.
 */
public class GifFragment extends BaseFragment2 implements GifFragmentView{

    private GifAdapter mGifAdapter;
    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private GifFragmentPresenter mPresenter;

    public static GifFragment newInstance() {
        GifFragment fragment = new GifFragment();
        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new GifFragmentPresenter(getContext(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_gif;
    }

    @Override
    protected void onInitView(View view) {
        pullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) view.findViewById(R.id.recyclerView);
        pullLoadMoreRecyclerView.setLinearLayout();
        mGifAdapter = new GifAdapter();
        pullLoadMoreRecyclerView.setAdapter(mGifAdapter);

        final int bottom = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getContext().getResources().getDisplayMetrics()));

        pullLoadMoreRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = bottom;
                }
                outRect.bottom = bottom;
                outRect.left = bottom;
                outRect.right = bottom;
            }
        });

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
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mPresenter.refreshData();
    }

//    public void randomReFresh() {
//        if (pullLoadMoreRecyclerView.isLoadMore()) {
//            Toast.makeText(getContext(), "正在加载更多，请稍后刷新", Toast.LENGTH_SHORT).show();
//            mSwipeRefreshLayout.setRefreshing(false);
//            return;
//        }
//
//        mPage = (int) (Math.random() * 18 + 1);
//        postAsynHttp(mPage, true);
//    }

    @Override
    public void onShowSuccessView(List<GifInfo> gifInfos) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(true);
        mGifAdapter.setData(gifInfos);
        mGifAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowEmptyView() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void onShowErrorView() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void onLoadMoreSuccess(List<GifInfo> beautyInfo, boolean isHasMore) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(isHasMore);
        int startPosition = mGifAdapter.getItemCount();
        mGifAdapter.setMoreData(beautyInfo);
        mGifAdapter.notifyItemRangeInserted(startPosition, beautyInfo.size());
    }

    @Override
    public void onLoadMoreFail() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }
}
