package com.maverick;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.maverick.base.BaseActivity;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.BrowsingHistoryActivityPresenter;

/**
 * Created by limingfei on 2017/9/27.
 */
public class BrowsingHistoryActivity extends BaseActivity {

    private BrowsingHistoryActivityPresenter mPresenter;

    public static void launch(Context context) {
        Intent intent = new Intent(context, BrowsingHistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new BrowsingHistoryActivityPresenter();
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_history;
    }

    @Override
    protected void onInitView() {
        RecyclerView recyclerView = findView(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mPresenter.loadData();
    }
}
