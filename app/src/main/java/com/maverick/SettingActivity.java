package com.maverick;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.maverick.base.BaseActivity;
import com.maverick.presenter.BasePresenter;

/**
 * Created by limingfei on 2017/12/22.
 */

public class SettingActivity extends BaseActivity {

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onInitView() {
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("设置");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

    }
}
