package com.maverick.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.maverick.R;
import com.maverick.adapter.PearItemFragmentAdapter;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.PearVideoInfo;
import com.maverick.bean.PearVideoTabInfo;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.PearItemFragmentPresenter;
import com.maverick.presenter.implView.IPearItemFragmentView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public class PearItemFragment extends BaseFragment2 implements IPearItemFragmentView {

    private static final String ARGUMENT = "ARGUMENT";

    private static final int spanCount = 2;

    private PearItemFragmentPresenter mPresenter;
    private PearItemFragmentAdapter mAdapter;
    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;

    private ViewGroup root;
    private PearVideoTabInfo mInfo;

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
        root = findView(R.id.root);

        pullLoadMoreRecyclerView = findView(R.id.recyclerView);
        RecyclerView recyclerView = pullLoadMoreRecyclerView.getRecyclerView();
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new PearItemFragmentAdapter(getContext());
        recyclerView.setAdapter(mAdapter);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                if (PearItemFragmentAdapter.TYPE_GALLERY == mAdapter.getItemViewType(position)) {
                    return spanCount;
                }
                return 1;
            }
        });

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);

                if (mAdapter != null && mAdapter.isHeader()) {
                    if (position > 0) {
                        position--;
                    } else {
                        return;
                    }
                }

                outRect.bottom = getResources().getDimensionPixelSize(R.dimen.x2);
                outRect.top = getResources().getDimensionPixelSize(R.dimen.y9);
                if (position % 2 == 0) {
                    outRect.right = getResources().getDimensionPixelSize(R.dimen.y1);
                } else {
                    outRect.left = getResources().getDimensionPixelSize(R.dimen.y1);
                }
            }
        });

        pullLoadMoreRecyclerView.setHasMore(false);

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadData(mInfo);
            }

            @Override
            public void onLoadMore() {
                mPresenter.loadMoreData();
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mInfo = (PearVideoTabInfo) getArguments().getSerializable(ARGUMENT);
        mPresenter.loadData(mInfo);
    }

    @Override
    public void onShowSuccessView(List<PearVideoInfo> hotList, List<PearVideoInfo> list, boolean isHasMore) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(isHasMore);
        mAdapter.setData(hotList, list);
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
    public void onLoadMoreSuccess(List<PearVideoInfo> list, boolean isHasMore) {
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
