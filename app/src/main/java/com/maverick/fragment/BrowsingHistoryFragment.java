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
import com.maverick.adapter.BrowsingHistoryActivityAdapter;
import com.maverick.model.HistoryModel;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.BrowsingHistoryActivityPresenter;
import com.maverick.presenter.implView.IBrowsingHistoryActivityView;
import com.maverick.util.DensityUtil;
import com.maverick.util.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cntv.greendaolibrary.dbbean.History;

/**
 * Created by limingfei on 2017/9/27.
 */
public class BrowsingHistoryFragment extends BaseEditFragment implements IBrowsingHistoryActivityView {

    private String TAG = getClass().getSimpleName();

    private BrowsingHistoryActivityPresenter mPresenter;
    private BrowsingHistoryActivityAdapter mBrowsingHistoryActivityAdapter;
    private GridLayoutManager mGridLayoutManager;
    private int spanCount = 4;
    private RecyclerView recyclerView;
    private ViewGroup root;

    public static BrowsingHistoryFragment newInstance() {
        BrowsingHistoryFragment fragment = new BrowsingHistoryFragment();
        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new BrowsingHistoryActivityPresenter(getContext(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_history;
    }

    @Override
    protected void onInitView(View view) {

        root = findView(R.id.root);

        recyclerView = findView(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(getContext(), spanCount);
        mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mGridLayoutManager);

        mBrowsingHistoryActivityAdapter = new BrowsingHistoryActivityAdapter(getContext());
        recyclerView.setAdapter(mBrowsingHistoryActivityAdapter);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                RecyclerView.ViewHolder viewHolder = parent.findContainingViewHolder(view);

                int viewType = viewHolder.getItemViewType();

                outRect.bottom = DensityUtil.dip2px(getContext(), 10);

                switch (viewType) {
                    case BrowsingHistoryActivityAdapter.TITLE:
                        if (parent.getChildAdapterPosition(view) == 0) {
                            outRect.top = DensityUtil.dip2px(getContext(), 10);
                        }
                        break;
                    case BrowsingHistoryActivityAdapter.IMAGE:
                        outRect.left = DensityUtil.dip2px(getContext(), 5);
                        outRect.right = DensityUtil.dip2px(getContext(), 5);
                        break;
                }
            }
        });

        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mBrowsingHistoryActivityAdapter.getItemViewType(position) == BrowsingHistoryActivityAdapter.TITLE) {
                    return 4;
                }
                return 1;
            }
        });

        mBrowsingHistoryActivityAdapter.setOnAdapterListener(new BrowsingHistoryActivityAdapter.OnAdapterListener() {
            @Override
            public void onItemClick(History history) {
                checkState = getClickCheckState();
                if (mOnBaseEditFragmentListener != null) {
                    mOnBaseEditFragmentListener.onCheckState(checkState);
                }
            }
        });
    }

    public int getClickCheckState() {

        int checkState = STATE_NO_ALL_CHECK;

        List<Object> list = mBrowsingHistoryActivityAdapter.getData();

        if (list == null || list.size() < 1) {
            return checkState;
        }

        List<Boolean> checks = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof History) {
                History history = (History) list.get(i);
                checks.add(history.isCheck());
            }
        }

        boolean isCheck = true;
        boolean isOneCheck = false;

        for (int i = 0; i < checks.size(); i++) {
            if (true != checks.get(i)) {
                isCheck = false;
            } else {
                isOneCheck = true;
            }
        }

        return isCheck ? STATE_ALL_CHECK : isOneCheck ? STATE_CHECK : STATE_NO_ALL_CHECK;
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mPresenter.loadData();
    }

    @Override
    public void onShowSuccessView(List<Object> histories) {
        mBrowsingHistoryActivityAdapter.setData(histories);
        mBrowsingHistoryActivityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowEmptyView() {

        View view = View.inflate(root.getContext(), R.layout.view_empty, null);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;

        root.addView(view, layoutParams);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.removeView(v);
                mPresenter.loadData();
            }
        });
    }

    @Override
    protected void openEditState() {
        super.openEditState();
        mBrowsingHistoryActivityAdapter.setEditState(true);
        List<Object> list = mBrowsingHistoryActivityAdapter.getData();

        if (list == null || list.size() < 1) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof History) {
                History history = (History) list.get(i);
                history.setCheck(false);
                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
                if (holder == null) {
                    mBrowsingHistoryActivityAdapter.notifyItemChanged(i);
                } else {
                    BrowsingHistoryActivityAdapter.HistoryImageHolder historyImageHolder = (BrowsingHistoryActivityAdapter.HistoryImageHolder) holder;
                    historyImageHolder.setCheck(View.VISIBLE, false);
                }
            }
        }
    }

    @Override
    protected void closeEditState() {
        super.closeEditState();
        List<Object> list = mBrowsingHistoryActivityAdapter.getData();

        if (list == null || list.size() < 1) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof History) {
                History history = (History) list.get(i);
                history.setCheck(false);
                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
                if (holder == null) {
                    mBrowsingHistoryActivityAdapter.notifyItemChanged(i);
                } else {
                    BrowsingHistoryActivityAdapter.HistoryImageHolder historyImageHolder = (BrowsingHistoryActivityAdapter.HistoryImageHolder) holder;
                    historyImageHolder.setCheck(View.INVISIBLE, false);
                }
            }
        }
    }

    @Override
    public boolean isSelectorAll() {
        List<Object> list = mBrowsingHistoryActivityAdapter.getData();

        if (list == null || list.size() < 1) {
            return false;
        }

        return true;
    }

    @Override
    public void selectorAll() {
        super.selectorAll();

        List<Object> list = mBrowsingHistoryActivityAdapter.getData();

        if (list == null || list.size() < 1) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof History) {
                History history = (History) list.get(i);
                if (history.isCheck()) {
                    //已经选中的继续下次循环
                    continue;
                }
                history.setCheck(true);
                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
                if (holder == null) {
                    mBrowsingHistoryActivityAdapter.notifyItemChanged(i);
                } else {
                    BrowsingHistoryActivityAdapter.HistoryImageHolder historyImageHolder = (BrowsingHistoryActivityAdapter.HistoryImageHolder) holder;
                    historyImageHolder.setCheck(true);
                }
            }
        }
    }

    @Override
    public void cancelAll() {
        List<Object> list = mBrowsingHistoryActivityAdapter.getData();

        if (list == null || list.size() < 1) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof History) {
                History history = (History) list.get(i);
                if (!history.isCheck()) {
                    //没选中的继续下一次循环
                    continue;
                }
                history.setCheck(false);
                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
                if (holder == null) {
                    mBrowsingHistoryActivityAdapter.notifyItemChanged(i);
                } else {
                    BrowsingHistoryActivityAdapter.HistoryImageHolder historyImageHolder = (BrowsingHistoryActivityAdapter.HistoryImageHolder) holder;
                    historyImageHolder.setCheck(false);
                }
            }
        }
    }

    @Override
    public void delete() {
        List<Object> list = mBrowsingHistoryActivityAdapter.getData();
        if (list == null || list.size() < 1) {
            return;
        }

        List<History> tempList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof History) {
                History history = (History) list.get(i);
                if (history.isCheck()) {
                    tempList.add(history);
                }
            }
        }

        if (tempList.size() == 1) {
            History history = tempList.get(0);
            mBrowsingHistoryActivityAdapter.notifyItemRemoved(list.indexOf(history));
            mBrowsingHistoryActivityAdapter.notifyItemChanged(list.indexOf(history));
            list.remove(history);
            HistoryModel.newInstance().deleteHistoryDB(history);
        } else if (tempList.size() > 1) {
            list.removeAll(tempList);
            mBrowsingHistoryActivityAdapter.notifyDataSetChanged();
            HistoryModel.newInstance().deleteHistoryDBList(tempList);
        }

        boolean today = false;
        boolean sevenDay = false;
        boolean earlier = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof History) {
                History history = (History) list.get(i);
                long time = history.getHistoryTime();
                if (TimeUtils.getStartTimeOfDay(new Date()) < time) {
                    //表示还有今天的数据
                    today = true;
                } else if (TimeUtils.getSevenDayStartTimeOfDay() <= time && TimeUtils.getSevenDayStartTimeOfDay() <= TimeUtils.getStartTimeOfDay(new Date())) {
                    sevenDay = true;
                } else {
                    earlier = true;
                }
            }
        }

        String todayStr = getString(R.string.history_today);
        String sevenDayStr = getString(R.string.history_seven_day);
        String earlierStr = getString(R.string.history_earlier);

        if (!today && list.contains(todayStr)) {
            list.remove(todayStr);
            int index = list.indexOf(todayStr);
            mBrowsingHistoryActivityAdapter.notifyItemRemoved(index);
        } else if (!sevenDay && list.contains(sevenDayStr)) {
            list.remove(sevenDayStr);
            int index = list.indexOf(sevenDayStr);
            mBrowsingHistoryActivityAdapter.notifyItemRemoved(index);
        } else if (!earlier && list.contains(earlierStr)) {
            list.remove(earlierStr);
            int index = list.indexOf(earlierStr);
            mBrowsingHistoryActivityAdapter.notifyItemRemoved(index);
        }

        checkState = STATE_NO_ALL_CHECK;
        if (mOnBaseEditFragmentListener != null) {
            mOnBaseEditFragmentListener.onCheckState(checkState);
        }

        if (list.size() == 0) {
            onShowEmptyView();
        }
    }
}
