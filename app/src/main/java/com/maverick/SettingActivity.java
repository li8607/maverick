package com.maverick;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;

import com.maverick.base.BaseActivity;
import com.maverick.dialog.ThemeDialog;
import com.maverick.global.ActivityCode;
import com.maverick.global.SPKey;
import com.maverick.presenter.BasePresenter;
import com.maverick.util.PreferenceUtil;

/**
 * Created by limingfei on 2017/12/22.
 */

public class SettingActivity extends BaseActivity implements ThemeDialog.OnThemeChangeListener {

    private Toolbar mToolbar;
    private AppBarLayout mAppBarLayout;
    private SwitchCompat switchCompat;
    private int mDefaultNightMode;

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

        switchCompat = findView(R.id.switchCompat);
        mDefaultNightMode = getDelegate().getDefaultNightMode();
        switchCompat.setChecked(mDefaultNightMode == AppCompatDelegate.MODE_NIGHT_YES);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    PreferenceUtil.getInstance(getApplicationContext()).putInt(SPKey.NIGHT, 1);
                } else {
                    getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    PreferenceUtil.getInstance(getApplicationContext()).putInt(SPKey.NIGHT, 0);
                }

                if (mDefaultNightMode != getDelegate().getDefaultNightMode()) {
                    Intent intent = new Intent();
                    intent.putExtra("RESULT_CODE_MODE_NIGHT", getDelegate().getDefaultNightMode());
                    setResult(ActivityCode.RESULT_CODE_MODE_NIGHT, intent);
                    refreshUI();
                }
            }
        });

        View cv_more_theme = findView(R.id.cv_more_theme);
        cv_more_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeDialog dialog = ThemeDialog.newInstance();
                dialog.setOnThemeChangeListener(SettingActivity.this);
                showDialogFragment(dialog);
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onThemeChange(int themeType) {
        PreferenceUtil.getInstance(MainApp.mContext).putInt(SPKey.THEME, themeType);
        setResult(ActivityCode.RESULT_CODE_THEME);
        setTheme(MainApp.getInstance().getCustomTheme());
        this.refreshUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void refreshUI() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        mToolbar.setBackgroundResource(typedValue.resourceId);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(typedValue.resourceId));
        }
        switchCompat.setThumbTintList(ContextCompat.getColorStateList(this, typedValue.resourceId));
    }
}
