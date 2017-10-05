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
import com.maverick.adapter.CollectItemBeautyFragmentAdapter;
import com.maverick.bean.CollectTabInfo;
import com.maverick.global.Tag;
import com.maverick.model.CollectModel;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.CollectItemBeautyFragmentPresenter;
import com.maverick.presenter.implView.ICollectItemBeautyFragmentView;
import com.maverick.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/9/29.
 */
public class CollectItemBeautyFragment extends BaseEditFragment implements ICollectItemBeautyFragmentView {

    private static final int spanCount = 3;
    private CollectItemBeautyFragmentAdapter mCollectItemBeautyFragmentAdapter;
    private CollectItemBeautyFragmentPresenter mPresenter;
    private RecyclerView recyclerView;
    private ViewGroup root;

    public static CollectItemBeautyFragment newInstance(CollectTabInfo collectTabInfo) {
        CollectItemBeautyFragment fragment = new CollectItemBeautyFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag.KEY_INFO, collectTabInfo);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new CollectItemBeautyFragmentPresenter(getContext(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.item_collect_beauty;
    }

    @Override
    protected void onInitView(View view) {
        super.onInitView(view);

        root = findView(R.id.root);

        recyclerView = findView(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mCollectItemBeautyFragmentAdapter = new CollectItemBeautyFragmentAdapter(getContext());
        recyclerView.setAdapter(mCollectItemBeautyFragmentAdapter);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = DensityUtil.dip2px(getContext(), 10);
                outRect.left = DensityUtil.dip2px(getContext(), 5);
                outRect.right = DensityUtil.dip2px(getContext(), 5);
            }
        });

        mCollectItemBeautyFragmentAdapter.setOnAdapterListener(new CollectItemBeautyFragmentAdapter.OnAdapterListener() {
            @Override
            public void onItemClick(int position, Collect collect) {
                checkState = getClickCheckState();
                if (mOnBaseEditFragmentListener != null) {
                    mOnBaseEditFragmentListener.onCheckState(checkState);
                }
            }
        });
    }

    public int getClickCheckState() {

        int checkState = STATE_NO_ALL_CHECK;

        List<Collect> list = mCollectItemBeautyFragmentAdapter.getData();

        if (list == null || list.size() < 1) {
            return checkState;
        }

        List<Boolean> checks = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Collect collect = list.get(i);
            checks.add(collect.getCheck());
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
        super.onInitData(savedInstanceState);
        CollectTabInfo collectTabInfo = (CollectTabInfo) getArguments().getSerializable(Tag.KEY_INFO);
        mPresenter.loadData();
    }

    @Override
    protected void openEditState() {
        super.openEditState();
        mCollectItemBeautyFragmentAdapter.setEditState(true);

        List<Collect> list = mCollectItemBeautyFragmentAdapter.getData();

        if (list == null || list.size() < 1) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Collect collect = list.get(i);
            collect.setCheck(false);
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
            if (holder == null) {
                mCollectItemBeautyFragmentAdapter.notifyItemChanged(i);
            } else {
                CollectItemBeautyFragmentAdapter.CollectBeautyViewHolder collectBeautyViewHolder = (CollectItemBeautyFragmentAdapter.CollectBeautyViewHolder) holder;
                collectBeautyViewHolder.setCheckViewVisible(View.VISIBLE, false);
            }
        }
    }

    @Override
    protected void closeEditState() {
        super.closeEditState();
        mCollectItemBeautyFragmentAdapter.setEditState(false);

        List<Collect> list = mCollectItemBeautyFragmentAdapter.getData();

        if (list == null || list.size() < 1) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Collect collect = list.get(i);
            collect.setCheck(false);
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
            if (holder == null) {
                mCollectItemBeautyFragmentAdapter.notifyItemChanged(i);
            } else {
                CollectItemBeautyFragmentAdapter.CollectBeautyViewHolder collectBeautyViewHolder = (CollectItemBeautyFragmentAdapter.CollectBeautyViewHolder) holder;
                collectBeautyViewHolder.setCheckViewVisible(View.INVISIBLE, false);
            }
        }
    }

    @Override
    public boolean isSelectorAll() {
        List<Collect> list = mCollectItemBeautyFragmentAdapter.getData();

        if (list == null || list.size() < 1) {
            return false;
        }

        return true;
    }

    @Override
    public void selectorAll() {
        super.selectorAll();

        List<Collect> list = mCollectItemBeautyFragmentAdapter.getData();

        if (list == null || list.size() < 1) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Collect collect = list.get(i);
            if (collect.getCheck()) {
                continue;
            }
            collect.setCheck(true);

            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
            if (holder == null) {
                mCollectItemBeautyFragmentAdapter.notifyItemChanged(i);
            } else {
                CollectItemBeautyFragmentAdapter.CollectBeautyViewHolder collectBeautyViewHolder = (CollectItemBeautyFragmentAdapter.CollectBeautyViewHolder) holder;
                collectBeautyViewHolder.setCheck(true);
            }
        }
    }

    @Override
    public void cancelAll() {
        super.cancelAll();
        List<Collect> list = mCollectItemBeautyFragmentAdapter.getData();

        if (list == null || list.size() < 1) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Collect collect = list.get(i);
            if (!collect.getCheck()) {
                continue;
            }
            collect.setCheck(false);

            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
            if (holder == null) {
                mCollectItemBeautyFragmentAdapter.notifyItemChanged(i);
            } else {
                CollectItemBeautyFragmentAdapter.CollectBeautyViewHolder collectBeautyViewHolder = (CollectItemBeautyFragmentAdapter.CollectBeautyViewHolder) holder;
                collectBeautyViewHolder.setCheck(false);
            }
        }
    }

    @Override
    public void delete() {
        super.delete();
        List<Collect> list = mCollectItemBeautyFragmentAdapter.getData();

        if (list == null || list.size() < 1) {
            return;
        }

        List<Collect> tempList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Collect collect = list.get(i);
            if (collect.getCheck()) {
                tempList.add(collect);
            }
        }

        if (tempList.size() == 1) {
            Collect collect = tempList.get(0);
            mCollectItemBeautyFragmentAdapter.notifyItemRemoved(list.indexOf(collect));
            mCollectItemBeautyFragmentAdapter.notifyItemChanged(list.indexOf(collect));
            list.remove(collect);
            CollectModel.newInstance().deleteCollectDB(collect);
        } else if (tempList.size() > 1) {
            list.removeAll(tempList);
            mCollectItemBeautyFragmentAdapter.notifyDataSetChanged();
            CollectModel.newInstance().deleteCollectDBList(tempList);
        }

        checkState = STATE_NO_ALL_CHECK;
        if (mOnBaseEditFragmentListener != null) {
            mOnBaseEditFragmentListener.onCheckState(checkState);
        }

        if (list.size() == 0) {
            onShowEmptyView();
        }
    }

    @Override
    public void onShowSuccessView(List<Collect> collects) {
        mCollectItemBeautyFragmentAdapter.setData(collects);
        mCollectItemBeautyFragmentAdapter.notifyDataSetChanged();
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
}
