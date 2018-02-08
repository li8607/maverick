package com.maverick;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
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
    private boolean themeChange = false;

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
                themeChange = true;
                recreate();
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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("themeChange", themeChange);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {

            FragmentManager fm = getSupportFragmentManager();
            ThemeDialog themeDialog = (ThemeDialog) fm.findFragmentByTag(ThemeDialog.class.getName());
            if (themeDialog != null) {
                themeDialog.setOnThemeChangeListener(this);
            }

            if (savedInstanceState.getBoolean("themeChange", false)) {
                setResult(ActivityCode.RESULT_CODE_THEME);
            }
        }
    }

    @Override
    public void onThemeChange(int themeType) {
        PreferenceUtil.getInstance(MainApp.mContext).putInt(SPKey.THEME, themeType);
        setTheme(MainApp.getInstance().getCustomTheme());
        setResult(ActivityCode.RESULT_CODE_THEME);
        themeChange = true;
        recreate();
    }

    /**
     * 展示一个切换动画
     */
    private void showAnimation() {
        final View decorView = getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorView);
        if (decorView instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(this);
            view.setBackgroundDrawable(new BitmapDrawable(getResources(), cacheBitmap));
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorView).addView(view, layoutParam);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(10000);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorView).removeView(view);
                }
            });
            objectAnimator.start();
        }
    }

    /**
     * 获取一个 View 的缓存视图
     *  @param view
     *  @return
     */
    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

}
