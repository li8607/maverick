package com.maverick.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.adapter.ImgAdapter;
import com.maverick.bean.GifInfo;
import com.maverick.presenter.ImgFragmentPresenter;
import com.maverick.presenter.implPresenter.ImgFragmentPresenterImpl;
import com.maverick.presenter.implView.ImgFragmentView;

import java.util.List;

/**
 * Created by ll on 2017/5/22.
 */
public class ImgFragment extends Fragment implements ImgFragmentView {

    private ImgAdapter mImgAdapter;
    private ImgFragmentPresenter mImplImgFragmentPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private TextView mError;

    public static ImgFragment newInstance() {
        ImgFragment imgFragment = new ImgFragment();
        return imgFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mImplImgFragmentPresenter = new ImgFragmentPresenterImpl(getContext(), this);

        View view = inflater.inflate(R.layout.fragment_img, null);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mImgAdapter = new ImgAdapter(getContext());
        mRecyclerView.setAdapter(mImgAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    mPage = 1;
                    mImplImgFragmentPresenter.getImgList(mPage, true);
                }
            }
        });

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

        RecyclerView.OnScrollListener loadmoreListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //向下滚动
                {
                    int visibleItemCount = mLinearLayoutManager.getChildCount();
                    int totalItemCount = mLinearLayoutManager.getItemCount();
                    int pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount && !mSwipeRefreshLayout.isRefreshing()) {
                        isLoading = true;
                        mPage += 1;
                        loadMoreDate();
                    }
                }
            }
        };
        mRecyclerView.addOnScrollListener(loadmoreListener);

        mProgressBar = (ProgressBar) view.findViewById(R.id.loading);

        mError = (TextView) view.findViewById(R.id.error);

        mError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mError.setVisibility(View.INVISIBLE);
                mPage = 1;
                mImplImgFragmentPresenter.getImgList(mPage, true);
            }
        });

        return view;
    }

    private void loadMoreDate() {
        mImplImgFragmentPresenter.getImgList(mPage, false);
    }

    private int mPage = 1;

    private boolean isLoading;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefreshLayout.setRefreshing(true);
        mImplImgFragmentPresenter.getImgList(mPage, true);
    }

    @Override
    public void showLoading() {
        if (mImgAdapter.getItemCount() <= 0) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showError() {

        if (isLoading) {
            isLoading = false;
            return;
        }

        mError.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void refreshAdapter(List<GifInfo> list) {
        isLoading = false;
        mSwipeRefreshLayout.setRefreshing(false);
        mImgAdapter.setData(list);
        mImgAdapter.notifyDataSetChanged();
        mError.setVisibility(View.GONE);
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }
}
