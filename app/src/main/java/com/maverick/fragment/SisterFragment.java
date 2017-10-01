package com.maverick.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.maverick.R;
import com.maverick.adapter.SisterFragmentAdapter;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.SisterDetailInfo;
import com.maverick.bean.SisterInfo;
import com.maverick.global.Tag;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.SisterFragmentPresenter;
import com.maverick.presenter.implView.ISisterFragmentView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterFragment extends BaseFragment2 implements ISisterFragmentView {

    private SisterFragmentAdapter mSisterFragmentAdapter;
    private SisterFragmentPresenter mPresenter;
    private SisterDetailInfo mSisterDetailInfo;
    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;

    public static SisterFragment newInstance(SisterDetailInfo sisterDetailInfo) {
        SisterFragment fragment = new SisterFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag.KEY_INFO, sisterDetailInfo);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new SisterFragmentPresenter(getContext(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_sister;
    }

    @Override
    protected void onInitView(View view) {
        pullLoadMoreRecyclerView = findView(R.id.recyclerView);
        RecyclerView recyclerView = pullLoadMoreRecyclerView.getRecyclerView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mSisterFragmentAdapter = new SisterFragmentAdapter(getContext());
        recyclerView.setAdapter(mSisterFragmentAdapter);

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData(mSisterDetailInfo);
            }

            @Override
            public void onLoadMore() {
                mPresenter.loadMoreData();
            }
        });

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = getResources().getDimensionPixelSize(R.dimen.x1);
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mSisterDetailInfo = (SisterDetailInfo) getArguments().getSerializable(Tag.KEY_INFO);
        mPresenter.refreshData(mSisterDetailInfo);
    }

    @Override
    public void onShowSuccessView(List<SisterInfo> sisterInfos) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(true);
        mSisterFragmentAdapter.setData(sisterInfos);
        mSisterFragmentAdapter.notifyDataSetChanged();
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
    public void onLoadMoreSuccess(List<SisterInfo> list, boolean isHasMore) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(isHasMore);
        int positionStart = mSisterFragmentAdapter.getItemCount();
        mSisterFragmentAdapter.setMoreData(list);
        mSisterFragmentAdapter.notifyItemRangeInserted(positionStart, list.size());
    }

    @Override
    public void onLoadMoreFail() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }
}
