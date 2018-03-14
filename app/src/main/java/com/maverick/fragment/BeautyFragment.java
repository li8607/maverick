package com.maverick.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.adapter.BeautyFragmentAdapter;
import com.maverick.base.BaseFragment;
import com.maverick.bean.BeautyItemInfo;
import com.maverick.bean.BigImgInfo;
import com.maverick.hepler.BeanHelper;
import com.maverick.model.CollectModel;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.BeautyFragmentPresenter;
import com.maverick.presenter.implView.IBeautyFragmentView;
import com.maverick.util.DensityUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limingfei on 2017/9/26.
 */
public class BeautyFragment extends BaseFragment implements IBeautyFragmentView {

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private BeautyFragmentAdapter mBeautyFragmentAdapter;
    private BeautyFragmentPresenter mPresenter;
    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private ViewGroup root;

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

        root = findView(R.id.root);

        pullLoadMoreRecyclerView = findView(R.id.recyclerView);
        pullLoadMoreRecyclerView.setHasMore(true);
        pullLoadMoreRecyclerView.setPullRefreshEnable(true);

        recyclerView = pullLoadMoreRecyclerView.getRecyclerView();
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        mBeautyFragmentAdapter = new BeautyFragmentAdapter(getContext());
        recyclerView.setAdapter(mBeautyFragmentAdapter);

        mBeautyFragmentAdapter.setOnItemClickListener(new BeautyFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
                List<BeautyItemInfo> list = mPresenter.getBeautyInfos();
                if (list != null && list.size() > 0) {

                    List<BeautyItemInfo> tempList = new ArrayList<>();

                    for (int i = 0; i < list.size(); i++) {
                        if (Math.abs(position - i) < 5) {
                            tempList.add(list.get(i));
                        }
                    }

                    List<BigImgInfo> bigImgInfos = BeanHelper.getBeautyBigImgInfo(tempList);

                    BigImgInfo bigImgInfo = new BigImgInfo();
                    bigImgInfo.setImg(list.get(position).getUrl());

                    DetailActivity.launch(getActivity(), ((BeautyFragmentAdapter.BeautyHolder)viewHolder).image, bigImgInfos, bigImgInfo);
                }
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

        pullLoadMoreRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                int position = parent.getChildAdapterPosition(view);

                if (position < 2) {
                    outRect.top = DensityUtil.dip2px(getActivity(), 10);
                } else {
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
        pullLoadMoreRecyclerView.setRefreshing(true);
        pullLoadMoreRecyclerView.refresh();
    }

    @Override
    public void onShowSuccessView(List<BeautyItemInfo> beautyItemInfos, boolean isHasMore) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(isHasMore);
        mBeautyFragmentAdapter.setData(beautyItemInfos);
        mBeautyFragmentAdapter.notifyDataSetChanged();
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
    public void onLoadMoreSuccess(List<BeautyItemInfo> beautyInfo, int positionStart, int count, boolean isHasMore) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(isHasMore);
        mBeautyFragmentAdapter.setMoreData(beautyInfo);
        mBeautyFragmentAdapter.notifyItemRangeInserted(positionStart, count);
    }

    @Override
    public void onLoadMoreFail() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void onPause() {
        super.onPause();
        CollectModel.newInstance().addOnCollectListener(mListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        CollectModel.newInstance().removeOnCollectListener(mListener);
    }

    private CollectModel.OnCollectListener mListener = new CollectModel.OnCollectListener() {
        @Override
        public void onChange() {
            if (mPresenter != null && mBeautyFragmentAdapter != null) {
                mPresenter.checkCollect(mBeautyFragmentAdapter.getData());
                mBeautyFragmentAdapter.notifyDataSetChanged();
            }
        }
    };
}
