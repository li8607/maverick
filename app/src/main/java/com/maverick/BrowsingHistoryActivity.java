package com.maverick;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.maverick.adapter.BrowsingHistoryActivityAdapter;
import com.maverick.base.BaseActivity;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.BrowsingHistoryActivityPresenter;
import com.maverick.presenter.implView.IBrowsingHistoryActivityView;
import com.maverick.util.DensityUtil;

import java.util.List;

import cntv.greendaolibrary.dbbean.History;

/**
 * Created by limingfei on 2017/9/27.
 */
public class BrowsingHistoryActivity extends BaseActivity implements IBrowsingHistoryActivityView, View.OnClickListener {

    private BrowsingHistoryActivityPresenter mPresenter;
    private BrowsingHistoryActivityAdapter mBrowsingHistoryActivityAdapter;
    private GridLayoutManager mGridLayoutManager;
    private int spanCount = 4;

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

        View back = findView(R.id.back);
        back.setOnClickListener(this);
        TextView title = findView(R.id.title);
        title.setText("浏览记录");

        final RecyclerView recyclerView = findView(R.id.recyclerView);
        mGridLayoutManager = new GridLayoutManager(this, spanCount);
        mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mGridLayoutManager);

        mBrowsingHistoryActivityAdapter = new BrowsingHistoryActivityAdapter(this);
        recyclerView.setAdapter(mBrowsingHistoryActivityAdapter);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                int position = parent.getChildAdapterPosition(view);

                if (position % spanCount == 0) {
                    outRect.left = DensityUtil.dip2px(BrowsingHistoryActivity.this, 10);
                    outRect.right = DensityUtil.dip2px(BrowsingHistoryActivity.this, 5);
                } else if (position % spanCount == spanCount - 1) {
                    outRect.left = DensityUtil.dip2px(BrowsingHistoryActivity.this, 5);
                    outRect.right = DensityUtil.dip2px(BrowsingHistoryActivity.this, 10);
                } else {
                    outRect.left = DensityUtil.dip2px(BrowsingHistoryActivity.this, 5);
                    outRect.right = DensityUtil.dip2px(BrowsingHistoryActivity.this, 5);
                }

                if (position < spanCount) {
                    outRect.top = DensityUtil.dip2px(BrowsingHistoryActivity.this, 10);
                } else {
                    outRect.top = DensityUtil.dip2px(BrowsingHistoryActivity.this, 5);
                }

                outRect.bottom = DensityUtil.dip2px(BrowsingHistoryActivity.this, 5);
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mPresenter.loadData();
    }

    @Override
    public void onShowSuccessView(List<History> histories) {
        mBrowsingHistoryActivityAdapter.setData(histories);
        mBrowsingHistoryActivityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowEmptyView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
