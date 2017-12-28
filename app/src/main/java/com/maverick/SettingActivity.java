package com.maverick;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;

import com.maverick.base.BaseActivity;
import com.maverick.presenter.BasePresenter;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * Created by limingfei on 2017/12/22.
 */

public class SettingActivity extends BaseActivity {

    private Toolbar mToolbar;
    private AppBarLayout mAppBarLayout;

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

        mAppBarLayout = findView(R.id.appbar);

        mToolbar = findView(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("设置");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SwitchCompat switchCompat = findView(R.id.switchCompat);
        switchCompat.setChecked(MainApp.getInstance().getModeTheme() == 1);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainApp.getInstance().setModeTheme(SettingActivity.this, 1);
                } else {
                    MainApp.getInstance().setModeTheme(SettingActivity.this, 0);
                }

                try {
                    Activity preActivity = BGASwipeBackManager.getInstance().getPenultimateActivity(SettingActivity.this);
                    if (preActivity != null) {
                        preActivity.recreate();
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

    }
}
