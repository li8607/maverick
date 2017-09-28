package com.maverick;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.maverick.adapter.BrowsingHistoryActivityAdapter;
import com.maverick.base.BaseActivity;
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
public class BrowsingHistoryActivity extends BaseActivity implements IBrowsingHistoryActivityView, View.OnClickListener {

    private String TAG = getClass().getSimpleName();

    private static final int CHECK_ALL_STATE = 1;
    private static final int CHECK_NO_ALL_STATE = 2;
    private int check_state = CHECK_NO_ALL_STATE;

    private BrowsingHistoryActivityPresenter mPresenter;
    private BrowsingHistoryActivityAdapter mBrowsingHistoryActivityAdapter;
    private GridLayoutManager mGridLayoutManager;
    private int spanCount = 4;
    private View linearLayout;
    private Button btn_check_or_cancel;
    private Button btn_delete;
    private TextView edit;
    private View btn_root;
    private RecyclerView recyclerView;

    public static void launch(Context context) {
        Intent intent = new Intent(context, BrowsingHistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new BrowsingHistoryActivityPresenter(this, this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_history;
    }

    @Override
    protected void onInitView() {

        linearLayout = findView(R.id.linearLayout);
        btn_root = findView(R.id.btn_root);
        btn_check_or_cancel = findView(R.id.btn_check_or_cancel);
        btn_check_or_cancel.setOnClickListener(this);
        btn_delete = findView(R.id.btn_delete);
        btn_delete.setOnClickListener(this);

        View back = findView(R.id.back);
        back.setOnClickListener(this);
        TextView title = findView(R.id.title);
        title.setText("浏览记录");

        edit = findView(R.id.edit);
        edit.setOnClickListener(this);

        recyclerView = findView(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(this, spanCount);
        mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mGridLayoutManager);

        mBrowsingHistoryActivityAdapter = new BrowsingHistoryActivityAdapter(this);
        recyclerView.setAdapter(mBrowsingHistoryActivityAdapter);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                RecyclerView.ViewHolder viewHolder = parent.findContainingViewHolder(view);

                int viewType = viewHolder.getItemViewType();

                outRect.bottom = DensityUtil.dip2px(BrowsingHistoryActivity.this, 10);

                switch (viewType) {
                    case BrowsingHistoryActivityAdapter.TITLE:
                        if (parent.getChildAdapterPosition(view) == 0) {
                            outRect.top = DensityUtil.dip2px(BrowsingHistoryActivity.this, 10);
                        }
                        break;
                    case BrowsingHistoryActivityAdapter.IMAGE:
                        outRect.left = DensityUtil.dip2px(BrowsingHistoryActivity.this, 5);
                        outRect.right = DensityUtil.dip2px(BrowsingHistoryActivity.this, 5);
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

                if (history.isCheck()) {
                    if (isCheckAll()) {
                        check_state = CHECK_ALL_STATE;
                        btn_check_or_cancel.setText("取消全选");
                    } else {
                        check_state = CHECK_NO_ALL_STATE;
                        btn_check_or_cancel.setText("全选");
                    }

                    btn_delete.setAlpha(1.0f);
                    btn_delete.setClickable(true);
                } else {
                    check_state = CHECK_NO_ALL_STATE;
                    btn_check_or_cancel.setText("全选");

                    if (isCheck()) {
                        btn_delete.setAlpha(1.0f);
                        btn_delete.setClickable(true);
                    } else {
                        btn_delete.setAlpha(0.5f);
                        btn_delete.setClickable(false);
                    }

                }
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mPresenter.loadData();
    }

    @Override
    public void onShowSuccessView(List<Object> histories) {
        edit.setVisibility(View.VISIBLE);
        mBrowsingHistoryActivityAdapter.setData(histories);
        mBrowsingHistoryActivityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowEmptyView() {
        edit.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.btn_check_or_cancel:
                switch (check_state) {
                    case CHECK_ALL_STATE:
                        cancelAll();
                        break;
                    case CHECK_NO_ALL_STATE:
                        checkAll();
                        break;
                }
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.edit:
                int stateEdit = mBrowsingHistoryActivityAdapter.getStateEdit();
                switch (stateEdit) {
                    case BrowsingHistoryActivityAdapter.STATE_EDIT:
                        closeEdit();
                        break;
                    case BrowsingHistoryActivityAdapter.STATE_NO_EDIT:
                        openEdit();
                        break;
                }
                break;
        }
    }

    private void closeEdit() {
        check_state = CHECK_NO_ALL_STATE;
        btn_check_or_cancel.setText("全选");
        btn_delete.setAlpha(0.5f);
        btn_delete.setClickable(false);

        edit.setText("编辑");
        btn_root.setVisibility(View.GONE);
        mBrowsingHistoryActivityAdapter.setStateEdit(BrowsingHistoryActivityAdapter.STATE_NO_EDIT);
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

    private void openEdit() {
        check_state = CHECK_NO_ALL_STATE;
        btn_check_or_cancel.setText("全选");
        btn_delete.setAlpha(0.5f);
        btn_delete.setClickable(false);

        edit.setText("取消编辑");
        btn_root.setVisibility(View.VISIBLE);
        mBrowsingHistoryActivityAdapter.setStateEdit(BrowsingHistoryActivityAdapter.STATE_EDIT);
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

    public void delete() {
        List<Object> list = mBrowsingHistoryActivityAdapter.getData();
        if (list == null || list.size() < 1) {
            return;
        }

        List<Object> tempList = new ArrayList<>();
        tempList.addAll(list);

        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i) instanceof History) {
                History history = (History) tempList.get(i);
                if (history.isCheck()) {
                    list.remove(history);
                    mBrowsingHistoryActivityAdapter.notifyItemRemoved(i);
                    mBrowsingHistoryActivityAdapter.notifyItemChanged(i);
                    HistoryModel.newInstance().deleteHistoryDB(history);
                }
            }
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
            mBrowsingHistoryActivityAdapter.notifyItemChanged(index);
        } else if (!sevenDay && list.contains(sevenDayStr)) {
            list.remove(sevenDayStr);
            int index = list.indexOf(sevenDayStr);
            mBrowsingHistoryActivityAdapter.notifyItemRemoved(index);
            mBrowsingHistoryActivityAdapter.notifyItemChanged(index);
        } else if (!earlier && list.contains(earlierStr)) {
            list.remove(earlierStr);
            int index = list.indexOf(earlierStr);
            mBrowsingHistoryActivityAdapter.notifyItemRemoved(index);
            mBrowsingHistoryActivityAdapter.notifyItemChanged(index);
        }

        btn_delete.setAlpha(0.5f);
        btn_delete.setClickable(false);

        if (list.size() == 0) {
            closeEdit();
            onShowEmptyView();
        }
    }

    public boolean isCheck() {
        List<Object> list = mBrowsingHistoryActivityAdapter.getData();

        if (list == null || list.size() < 1) {
            return false;
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof History) {
                History history = (History) list.get(i);
                if (history.isCheck()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isCheckAll() {
        List<Object> list = mBrowsingHistoryActivityAdapter.getData();

        if (list == null || list.size() < 1) {
            return false;
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof History) {
                History history = (History) list.get(i);
                if (!history.isCheck()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void checkAll() {

        check_state = CHECK_ALL_STATE;
        btn_check_or_cancel.setText("取消全选");

        btn_delete.setAlpha(1.0f);
        btn_delete.setClickable(true);

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

    public void cancelAll() {

        check_state = CHECK_NO_ALL_STATE;
        btn_check_or_cancel.setText("全选");

        btn_delete.setAlpha(0.5f);
        btn_delete.setClickable(false);

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
}
