package com.maverick.fragment;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.maverick.R;
import com.maverick.adapter.SettingTabFragmentAdapter;
import com.maverick.adapter.SinaItemFragmentAdapter;
import com.maverick.base.BaseFragment;
import com.maverick.bean.SinaInfo;
import com.maverick.bean.SinaTabInfo;
import com.maverick.global.Tag;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.SinaItemFragmentPresenter;
import com.maverick.presenter.implView.ISinaItemFragmentView;
import com.maverick.util.DensityUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SinaItemFragment extends BaseFragment implements ISinaItemFragmentView {

    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private SinaItemFragmentAdapter mAdapter;
    private SinaItemFragmentPresenter mPresenter;
    private SinaTabInfo mInfo;

    private ViewGroup root;

    public static SinaItemFragment newInstance(SinaTabInfo info) {
        SinaItemFragment fragment = new SinaItemFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag.KEY_INFO, info);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new SinaItemFragmentPresenter(getContext(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_sister_item;
    }

    @Override
    protected void onInitView(View view) {
        final int space = 1;
        final Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorDivider2));

        root = findView(R.id.root);

        pullLoadMoreRecyclerView = findView(R.id.recyclerView);
        RecyclerView mRecyclerView = pullLoadMoreRecyclerView.getRecyclerView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new SinaItemFragmentAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData(mInfo);
            }

            @Override
            public void onLoadMore() {
                mPresenter.loadMoreData();
            }
        });

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = space;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                final int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    int position = parent.getChildAdapterPosition(child);
                    if (position == parent.getAdapter().getItemCount() - 1
                            || parent.getAdapter().getItemViewType(position) == SettingTabFragmentAdapter.TAB_TITLE
                            || parent.getAdapter().getItemViewType(position + 1) == SettingTabFragmentAdapter.TAB_TITLE) {
                        continue;
                    }

                    c.drawLine(parent.getLeft(), child.getBottom() + space, parent.getRight(), child.getBottom() + space, mPaint);
                }
            }
        });

//        mAdapter.setOnListener(new SinaHolder.OnListener() {
//            @Override
//            public void onMenuClick(final View view, SinaInfo info) {
//                if (info == null) {
//                    return;
//                }

//                MenuDetailInfo menuDetailInfo = BeanHelper.getMenuDetailInfo(info);
//                menuDetailInfo.setCollect(BeanHelper.getCollect(info));
//                MenuDialog dialog = MenuDialog.newInstance(menuDetailInfo);
//                dialog.setOnDismissListener(new MenuDialog.OnShareDialogListener() {
//                    @Override
//                    public void onDismiss() {
//                        if (view != null) {
//                            view.setSelected(false);
//                        }
//                    }
//                });
//                showDialogFragment(dialog);
//                if (view != null) {
//                    view.setSelected(true);
//                }
//            }
//        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mInfo = (SinaTabInfo) getArguments().getSerializable(Tag.KEY_INFO);
        pullLoadMoreRecyclerView.setRefreshing(true);
        pullLoadMoreRecyclerView.refresh();
    }

    @Override
    public void onShowSuccessView(List<SinaInfo> list, boolean isHasMore) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(isHasMore);
        mAdapter.setData(list);
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
    public void onLoadMoreSuccess(List<SinaInfo> list, boolean isHasMore) {
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
