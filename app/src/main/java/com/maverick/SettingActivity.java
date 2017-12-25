package com.maverick;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.maverick.base.BaseActivity;
import com.maverick.presenter.BasePresenter;
import com.maverick.theme.ColorsSetting;
import com.maverick.theme.SettingBasic;

/**
 * Created by limingfei on 2017/12/22.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

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

        SettingBasic ll_basic_theme = findView(R.id.ll_basic_theme);
        ll_basic_theme.setOnClickListener(this);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_basic_theme:
                new ColorsSetting(SettingActivity.this).chooseBaseTheme();
                break;
        }
    }

    @Override
    public void updateUiElements() {
        super.updateUiElements();
        findView(R.id.setting_background).setBackgroundColor(getBackgroundColor());
    }
}
