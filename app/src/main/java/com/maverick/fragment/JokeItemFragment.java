package com.maverick.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.maverick.R;
import com.maverick.adapter.JokeItemFragmentAdapter;
import com.maverick.base.BaseFragment;
import com.maverick.bean.GifInfo;
import com.maverick.bean.JokeTabInfo;
import com.maverick.global.Tag;
import com.maverick.model.CollectModel;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.JokeItemFragmentPresenter;
import com.maverick.presenter.implView.IJokeItemFragmentView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ll on 2017/5/22.
 */
public class JokeItemFragment extends BaseFragment implements IJokeItemFragmentView {

    private JokeItemFragmentPresenter mPresenter;
    private JokeItemFragmentAdapter mJokeItemFragmentAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private FrameLayout root;
    private JokeTabInfo mJokeTabInfo;

    public static JokeItemFragment newInstance(JokeTabInfo jokeTabInfo) {
        JokeItemFragment fragment = new JokeItemFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag.KEY_INFO, jokeTabInfo);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new JokeItemFragmentPresenter(getContext(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_joke_item;
    }

    @Override
    protected void onInitView(View view) {
        root = findView(R.id.root);

        pullLoadMoreRecyclerView = findView(R.id.recyclerView);

        RecyclerView mRecyclerView = pullLoadMoreRecyclerView.getRecyclerView();
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mJokeItemFragmentAdapter = new JokeItemFragmentAdapter(getContext());
        mRecyclerView.setAdapter(mJokeItemFragmentAdapter);


        final int bottom = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getContext().getResources().getDisplayMetrics()));

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
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
                mPresenter.refreshData(mJokeTabInfo);
            }

            @Override
            public void onLoadMore() {
                mPresenter.loadMoreData();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if(mJokeItemFragmentAdapter.getData() != null) {
            outState.putInt("page", mPresenter.getPage());
            outState.putSerializable("data", (Serializable) mJokeItemFragmentAdapter.getData());
            outState.putParcelable("state", pullLoadMoreRecyclerView.getLayoutManager().onSaveInstanceState());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mJokeTabInfo = (JokeTabInfo) getArguments().getSerializable(Tag.KEY_INFO);

        if (savedInstanceState != null) {
            mPresenter.setPage(savedInstanceState.getInt("page", 1));
            List<GifInfo> list = (List<GifInfo>) savedInstanceState.getSerializable("data");
            if (list != null && list.size() > 0) {
                Parcelable parcelable = savedInstanceState.getParcelable("state");
                if (parcelable != null) {
                    pullLoadMoreRecyclerView.getLayoutManager().onRestoreInstanceState(parcelable);
                }

                onShowSuccessView(list);
                return;
            }
        }

        pullLoadMoreRecyclerView.setRefreshing(true);
        pullLoadMoreRecyclerView.refresh();
//        mPresenter.refreshData();
    }

//    public void randomReFresh() {
//        if (!isLoading) {
//            mPage = (int) (Math.random() * 223 + 1);
//            mPresenter.getImgList(1, true);
//        }
//    }

    @Override
    public void onShowSuccessView(List<GifInfo> gifInfos) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(true);
        mJokeItemFragmentAdapter.setData(gifInfos);
        mJokeItemFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowEmptyView() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();

        View view = View.inflate(root.getContext(), R.layout.view_empty, null);

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
    public void onLoadMoreSuccess(List<GifInfo> beautyInfo, boolean isHasMore) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(isHasMore);
        int startPosition = mJokeItemFragmentAdapter.getItemCount();
        mJokeItemFragmentAdapter.setMoreData(beautyInfo);
        mJokeItemFragmentAdapter.notifyItemRangeInserted(startPosition, beautyInfo.size());
    }

    @Override
    public void onLoadMoreFail() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void onResume() {
        super.onResume();
        CollectModel.newInstance().removeOnCollectListener(mListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        CollectModel.newInstance().addOnCollectListener(mListener);
    }

    private CollectModel.OnCollectListener mListener = new CollectModel.OnCollectListener() {
        @Override
        public void onChange() {
            if (mPresenter != null && mJokeItemFragmentAdapter != null) {
                mPresenter.checkCollect(mJokeItemFragmentAdapter.getData());
                mJokeItemFragmentAdapter.notifyDataSetChanged();
            }
        }
    };

//    @Override
//    public void onDestroyView() {
//        unbindDrawables(getView());
//        super.onDestroyView();
//    }
//
//    private void unbindDrawables(View view) {
//        if (view.getBackground() != null) {
//            view.getBackground().setCallback(null);
//        }
//        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
//            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
//                unbindDrawables(((ViewGroup) view).getChildAt(i));
//            }
//            ((ViewGroup) view).removeAllViews();
//        }
//    }
}
