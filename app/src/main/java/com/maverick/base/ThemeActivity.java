package com.maverick.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.maverick.R;

import cntv.themelibrary.PreferenceUtil;
import cntv.themelibrary.Theme;
import cntv.themelibrary.ThemeHelper;
import cntv.themelibrary.Themeable;
import cntv.themelibrary.ViewUtil;

/**
 * Created by limingfei on 2017/12/25.
 */

public class ThemeActivity extends AppCompatActivity {

    private ThemeHelper themeHelper;
    private PreferenceUtil SP;

    private boolean coloredNavBar;
    private boolean obscuredStatusBar;
    private boolean applyThemeSingleImgAct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SP = PreferenceUtil.getInstance(getApplicationContext());
        themeHelper = new ThemeHelper(getApplicationContext());
    }

    public int getDialogStyle() {
        return themeHelper.getDialogStyle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTheme();
        updateUiElements();
    }

    public void updateTheme() {
        themeHelper.updateTheme();
        coloredNavBar = SP.getBoolean(getString(R.string.preference_colored_nav_bar), false);
        obscuredStatusBar = SP.getBoolean(getString(R.string.preference_translucent_status_bar), true);
        applyThemeSingleImgAct = SP.getBoolean(getString(R.string.preference_apply_theme_pager), true);
    }

    public ThemeHelper getThemeHelper() {
        return themeHelper;
    }

    public int getPrimaryColor() {
        return themeHelper.getPrimaryColor();
    }

    public int getCardBackgroundColor(){
        return themeHelper.getCardBackgroundColor();
    }

    public void setBaseTheme(Theme baseTheme) {
        themeHelper.setBaseTheme(baseTheme);
    }

    public int getBackgroundColor(){
        return themeHelper.getBackgroundColor();
    }

    public void updateUiElements() {
        for (View view : ViewUtil.getAllChildren(findViewById(android.R.id.content))) {
            if (view instanceof Themeable) ((Themeable) view).refreshTheme(getThemeHelper());
        }
    }
}
