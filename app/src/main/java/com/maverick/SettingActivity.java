package com.maverick;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.maverick.base.BaseActivity;
import com.maverick.presenter.BasePresenter;
import com.maverick.theme.ColorsSetting;
import com.maverick.theme.SettingBasic;

/**
 * Created by limingfei on 2017/12/22.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

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
        mToolbar.setBackgroundColor(getPrimaryColor());
        setStatusBarColor();
        switch (getBaseTheme()) {
            case DARK:
            case AMOLED:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mAppBarLayout.setElevation(getResources().getDimension(R.dimen.card_elevation));
                }
                break;
            case LIGHT:
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mAppBarLayout.setElevation(0);
                }
                break;
        }
    }
}
