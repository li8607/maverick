package com.maverick.fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.maverick.PearActivity;
import com.maverick.R;
import com.maverick.adapter.PearItemFragmentAdapter;
import com.maverick.adapter.holder.PearBannerViewHolder;
import com.maverick.adapter.holder.PearImageViewHolder;
import com.maverick.base.BaseFragment;
import com.maverick.bean.PearVideoInfo;
import com.maverick.bean.PearVideoTabInfo;
import com.maverick.hepler.BeanHelper;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.PearItemFragmentPresenter;
import com.maverick.presenter.implView.IPearItemFragmentView;
import com.maverick.util.DensityUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

                outRect.bottom = DensityUtil.dip2px(getContext(), 2);
                outRect.top = DensityUtil.dip2px(getContext(), 12);
                if (position % 2 == 0) {
                    outRect.right = DensityUtil.dip2px(getContext(), 1);
                } else {
                    outRect.left =  DensityUtil.dip2px(getContext(), 1);
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

    @Override
    public void refreshUI() {
        //让 RecyclerView 缓存在 Pool 中的 Item 失效
        //那么，如果是ListView，要怎么做呢？这里的思路是通过反射拿到 AbsListView 类中的 RecycleBin 对象，然后同样再用反射去调用 clear 方法
        Class<RecyclerView> recyclerViewClass = RecyclerView.class;
        try {
            Field declaredField = recyclerViewClass.getDeclaredField("mRecycler");
            declaredField.setAccessible(true);
            Method declaredMethod = Class.forName(RecyclerView.Recycler.class.getName()).getDeclaredMethod("clear", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(declaredField.get(mRecyclerView), new Object[0]);
            RecyclerView.RecycledViewPool recycledViewPool = mRecyclerView.getRecycledViewPool();
            recycledViewPool.clear();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        TypedValue textColorHighlight = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.textColorHighlight, textColorHighlight, true);

        if (mRecyclerView != null) {

            for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));

                if (holder != null && holder instanceof PearImageViewHolder) {
                    PearImageViewHolder pearImageViewHolder = (PearImageViewHolder) holder;
                    pearImageViewHolder.label.setTextColor(ContextCompat.getColor(getContext(), textColorHighlight.resourceId));
                }
            }
        }
    }
}
