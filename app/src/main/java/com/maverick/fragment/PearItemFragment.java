package com.maverick.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.maverick.PearActivity;
import com.maverick.R;
import com.maverick.adapter.PearItemFragmentAdapter;
import com.maverick.adapter.holder.PearBannerViewHolder;
import com.maverick.base.BaseFragment;
import com.maverick.bean.PearVideoInfo;
import com.maverick.bean.PearVideoTabDetailInfo;
import com.maverick.bean.PearVideoTabInfo;
import com.maverick.hepler.BeanHelper;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.PearItemFragmentPresenter;
import com.maverick.presenter.implView.IPearItemFragmentView;
import com.maverick.util.DensityUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public class PearItemFragment extends BaseFragment implements IPearItemFragmentView {

    private String TAG = getClass().getSimpleName();

    private static final String ARGUMENT = "ARGUMENT";

    private static final int spanCount = 2;

    private PearItemFragmentPresenter mPresenter;
    private PearItemFragmentAdapter mAdapter;
    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;

    private ViewGroup root;
    private PearVideoTabInfo mInfo;
    private RecyclerView mRecyclerView;

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
        mRecyclerView = pullLoadMoreRecyclerView.getRecyclerView();
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new PearItemFragmentAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                if (PearItemFragmentAdapter.TYPE_GALLERY == mAdapter.getItemViewType(position) || PearItemFragmentAdapter.TYPE_BANNER == mAdapter.getItemViewType(position)) {
                    return spanCount;
                }
                return 1;
            }
        });

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
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

                if (position % 2 == 0) {
                    outRect.right = DensityUtil.dip2px(getContext(), 2);
                } else {
                    outRect.left = DensityUtil.dip2px(getContext(), 2);
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

        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                RecyclerView.ViewHolder holder = mRecyclerView.findContainingViewHolder(view);
                if (holder != null && holder instanceof PearBannerViewHolder) {
                    PearBannerViewHolder pearBannerViewHolder = (PearBannerViewHolder) holder;
                    Log.e(TAG, "pearBannerViewHolder.onStart();");
                    if (getUserVisibleHint()) {
                        pearBannerViewHolder.onStart();
                    } else {
                        pearBannerViewHolder.onStop();
                    }
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                RecyclerView.ViewHolder holder = mRecyclerView.findContainingViewHolder(view);
                if (holder != null && holder instanceof PearBannerViewHolder) {
                    PearBannerViewHolder pearBannerViewHolder = (PearBannerViewHolder) holder;
                    pearBannerViewHolder.onStop();
                    Log.e(TAG, "pearBannerViewHolder.onStop();");
                }
            }
        });

        mAdapter.setOnListener(new PearItemFragmentAdapter.OnListener() {
            @Override
            public void onItemClick(PearVideoInfo info) {
                PearActivity.launch(getActivity(), BeanHelper.getPearVideoDetailBean(info));
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (mAdapter.getData() != null) {
            outState.putSerializable("data", (Serializable) mAdapter.getData());
            outState.putSerializable("data1", (Serializable) mAdapter.getHotList());
            outState.putBoolean("hasMore", pullLoadMoreRecyclerView.isHasMore());
            outState.putSerializable("info", mPresenter.getTabDetailInfo());
            outState.putParcelable("state", pullLoadMoreRecyclerView.getLayoutManager().onSaveInstanceState());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mInfo = (PearVideoTabInfo) getArguments().getSerializable(ARGUMENT);

        if (savedInstanceState != null) {
            mPresenter.setTabDetailInfo((PearVideoTabDetailInfo) savedInstanceState.getSerializable("info"));
            List<PearVideoInfo> list = (List<PearVideoInfo>) savedInstanceState.getSerializable("data");
            if (list != null) {
                Parcelable parcelable = savedInstanceState.getParcelable("state");
                if (parcelable != null) {
                    pullLoadMoreRecyclerView.getLayoutManager().onRestoreInstanceState(parcelable);
                }
                onShowSuccessView((List<PearVideoInfo>) savedInstanceState.getSerializable("data1"), list, savedInstanceState.getBoolean("hasMore", false));
                return;
            }
        }

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
        pullLoadMoreRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, "isVisibleToUser = " + isVisibleToUser);
        if (mRecyclerView != null) {
            RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForAdapterPosition(0);
            if (holder != null && holder instanceof PearBannerViewHolder) {
                PearBannerViewHolder pearBannerViewHolder = (PearBannerViewHolder) holder;
                if (isVisibleToUser) {
                    pearBannerViewHolder.onStart();
                } else {
                    pearBannerViewHolder.onStop();
                }
            }
        }
    }
}
