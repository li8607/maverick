package com.maverick.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.maverick.R;
import com.maverick.adapter.CollectItemBeautyFragmentAdapter;
import com.maverick.adapter.CollectItemJokeFragmentAdapter;
import com.maverick.adapter.holder.CollectJokeImgViewHolder;
import com.maverick.adapter.holder.CollectJokeTextViewHolder;
import com.maverick.bean.CollectTabInfo;
import com.maverick.global.Tag;
import com.maverick.model.CollectModel;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.CollectItemJokeFragmentPresenter;
import com.maverick.presenter.implView.ICollectItemJokeFragmentView;

import java.util.ArrayList;
import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/10/4.
 */
public class CollectItemJokeFragment extends BaseEditFragment implements ICollectItemJokeFragmentView {

    private CollectItemJokeFragmentAdapter mAdapter;
    private CollectItemJokeFragmentPresenter mPresenter;
    private RecyclerView recyclerView;
    private ViewGroup root;
    private CollectTabInfo mCollectTabInfo;

    public static CollectItemJokeFragment newInstance(CollectTabInfo collectTabInfo) {
        CollectItemJokeFragment fragment = new CollectItemJokeFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag.KEY_INFO, collectTabInfo);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new CollectItemJokeFragmentPresenter(getContext(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.item_collect_joke;
    }

    @Override
    protected void onInitView(View view) {
        super.onInitView(view);

        root = findView(R.id.root);

        recyclerView = findView(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        mAdapter = new CollectItemJokeFragmentAdapter(getContext());
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnCollectJokeTextViewHolderListener(new CollectJokeTextViewHolder.OnCollectJokeTextViewHolderListener() {
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

        List<Collect> list = mAdapter.getData();

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
        mCollectTabInfo = (CollectTabInfo) getArguments().getSerializable(Tag.KEY_INFO);
        mPresenter.loadData(mCollectTabInfo);
    }

    @Override
    protected void openEditState() {
        super.openEditState();
        mAdapter.setEditState(true);

        List<Collect> list = mAdapter.getData();

        if (list == null || list.size() < 1) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Collect collect = list.get(i);
            collect.setCheck(false);
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
            if (holder == null) {
                mAdapter.notifyItemChanged(i);
            } else {
                CollectJokeTextViewHolder collectJokeTextViewHolder = (CollectJokeTextViewHolder) holder;
                collectJokeTextViewHolder.setCheckViewVisible(View.VISIBLE, false, true);
            }
        }
    }

    @Override
    protected void closeEditState() {
        super.closeEditState();
        mAdapter.setEditState(false);

        List<Collect> list = mAdapter.getData();

        if (list == null || list.size() < 1) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Collect collect = list.get(i);
            collect.setCheck(false);
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
            if (holder == null) {
                mAdapter.notifyItemChanged(i);
            } else {
                CollectJokeTextViewHolder collectJokeTextViewHolder = (CollectJokeTextViewHolder) holder;
                collectJokeTextViewHolder.setCheckViewVisible(View.INVISIBLE, false, false);
            }
        }
    }

    @Override
    public boolean isSelectorAll() {
        List<Collect> list = mAdapter.getData();

        if (list == null || list.size() < 1) {
            return false;
        }

        return true;
    }

    @Override
    public void selectorAll() {
        super.selectorAll();

        List<Collect> list = mAdapter.getData();

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
                mAdapter.notifyItemChanged(i);
            } else {
                CollectJokeTextViewHolder collectJokeTextViewHolder = (CollectJokeTextViewHolder) holder;
                collectJokeTextViewHolder.setCheck(true);
            }
        }
    }

    @Override
    public void cancelAll() {
        super.cancelAll();
        List<Collect> list = mAdapter.getData();

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
                mAdapter.notifyItemChanged(i);
            } else {
                CollectJokeTextViewHolder collectJokeTextViewHolder = (CollectJokeTextViewHolder) holder;
                collectJokeTextViewHolder.setCheck(false);
            }
        }
    }

    @Override
    public void delete() {
        super.delete();
        List<Collect> list = mAdapter.getData();

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
            mAdapter.notifyItemRemoved(list.indexOf(collect));
            mAdapter.notifyItemChanged(list.indexOf(collect));
            list.remove(collect);
            CollectModel.newInstance().deleteCollectDB(collect);
        } else if (tempList.size() > 1) {
            list.removeAll(tempList);
            mAdapter.notifyDataSetChanged();
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
        mAdapter.setData(collects);
        mAdapter.notifyDataSetChanged();
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
                mPresenter.loadData(mCollectTabInfo);
            }
        });
    }
}
