package com.maverick.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.maverick.R;
import com.maverick.adapter.TextAdapter;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.GifInfo;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.TextFragmentPresenter2;
import com.maverick.presenter.implView.TextFragmentView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

/**
 * Created by ll on 2017/5/22.
 */
public class TextFragment extends BaseFragment2 implements TextFragmentView {

    private TextFragmentPresenter2 mPresenter;
    private TextAdapter mTextAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;

    public static TextFragment newInstance() {
        TextFragment fragment = new TextFragment();
        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new TextFragmentPresenter2(getContext(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_text;
    }

    @Override
    protected void onInitView(View view) {

        pullLoadMoreRecyclerView = findView(R.id.recyclerView);

        RecyclerView mRecyclerView = pullLoadMoreRecyclerView.getRecyclerView();
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
//        if (!isLoading) {
//            mPage = (int) (Math.random() * 223 + 1);
//            mPresenter.getImgList(1, true);
//        }
//    }

    @Override
    public void onShowSuccessView(List<GifInfo> gifInfos) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(true);
        mTextAdapter.setData(gifInfos);
        mTextAdapter.notifyDataSetChanged();
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
        int startPosition = mTextAdapter.getItemCount();
        mTextAdapter.setMoreData(beautyInfo);
        mTextAdapter.notifyItemRangeInserted(startPosition, beautyInfo.size());
    }

    @Override
    public void onLoadMoreFail() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }
}
