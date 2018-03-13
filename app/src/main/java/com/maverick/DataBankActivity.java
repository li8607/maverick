package com.maverick;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.maverick.base.BaseActivity;
import com.maverick.bean.CollectTabInfo;
import com.maverick.bean.MyInfo;
import com.maverick.fragment.BrowsingHistoryFragment;
import com.maverick.fragment.CollectFragment;
import com.maverick.global.Tag;
import com.maverick.presenter.BasePresenter;
import com.maverick.type.FragmentType;
import com.maverick.type.MyType;

/**
 * Created by limingfei on 2017/9/29.
 */
public class DataBankActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;

    public static void launch(Context context, MyInfo myInfo) {
        Intent intent = new Intent(context, DataBankActivity.class);
        intent.putExtra(Tag.KEY_INFO, myInfo);
        context.startActivity(intent);
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void onInitView() {


        mToolbar = findView(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        MyInfo myInfo = (MyInfo) getIntent().getSerializableExtra(Tag.KEY_INFO);
        Fragment fragment = getBaseFragment(myInfo);

        if (fragment == null) {
            return;
        }
        replaceFragment(R.id.collect_content, fragment);
    }

    public Fragment getBaseFragment(MyInfo myInfo) {
        Fragment fragment = null;
        if (myInfo.getType() == MyType.HISTORY) {
            //浏览记录
            fragment = BrowsingHistoryFragment.newInstance();
            if (!TextUtils.isEmpty(myInfo.getTitle())) {
                getSupportActionBar().setTitle(myInfo.getTitle());
            }
        } else if (myInfo.getType() == MyType.COLLECT) {
            CollectTabInfo collectTabInfo = new CollectTabInfo();
            collectTabInfo.setType(FragmentType.COLLECT);
            CollectFragment collectFragment = CollectFragment.newInstance(collectTabInfo);

            fragment = collectFragment;

            if (!TextUtils.isEmpty(myInfo.getTitle())) {
                getSupportActionBar().setTitle(myInfo.getTitle());
            }
        }

        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressedSupport();
                break;
        }
    }
}
