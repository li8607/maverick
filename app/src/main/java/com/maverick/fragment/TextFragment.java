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
import android.widget.TextView;

import com.maverick.R;
import com.maverick.adapter.TextAdapter;
import com.maverick.bean.GifInfo;
import com.maverick.presenter.implPresenter.TextFragmentPresenterImpl;
import com.maverick.presenter.implView.TextFragmentView;

import java.util.List;

/**
 * Created by ll on 2017/5/22.
 */
public class TextFragment extends BaseFragment implements TextFragmentView {

    private TextFragmentPresenterImpl mTextFragmentPresenterImpl;
    private TextAdapter mTextAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean isLoading;
    private int mPage = 1;
    private TextView error;
    private View loading;

    public static TextFragment newInstance() {
        TextFragment fragment = new TextFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mTextFragmentPresenterImpl = new TextFragmentPresenterImpl(getContext(), this);

        View view = inflater.inflate(R.layout.fragment_text, null);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mTextAdapter = new TextAdapter(getContext());
        mRecyclerView.setAdapter(mTextAdapter);


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

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    mPage = 1;
                    mTextFragmentPresenterImpl.getImgList(1, true);
                }
            }
        });

        error = (TextView) view.findViewById(R.id.error);
        loading = view.findViewById(R.id.loading);

        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                mTextFragmentPresenterImpl.getImgList(1, true);
            }
        });

        return view;
    }

    private void loadMoreDate() {
        mTextFragmentPresenterImpl.getImgList(mPage, true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loading.setVisibility(View.VISIBLE);
        mTextFragmentPresenterImpl.getImgList(1, true);
    }

    @Override
    public void refreshAdapter(List<GifInfo> list, boolean clean) {
        isLoading = false;
        mSwipeRefreshLayout.setRefreshing(false);
        error.setVisibility(View.GONE);
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);

        if (!clean) {
            mTextAdapter.setData(list);
            mTextAdapter.notifyDataSetChanged();
        } else {
            int startPosition = mTextAdapter.getItemCount();
            mTextAdapter.setMoreData(list);
            mTextAdapter.notifyItemRangeChanged(startPosition, list.size());
        }
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showError() {

        if (isLoading) {
            isLoading = false;
            return;
        }

        error.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void randomReFresh() {
        if (!isLoading) {
            mPage = (int) (Math.random() * 223 + 1);
            mTextFragmentPresenterImpl.getImgList(1, true);
        }
    }
}
