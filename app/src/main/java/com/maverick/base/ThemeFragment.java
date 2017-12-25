package com.maverick.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import cntv.themelibrary.Theme;
import cntv.themelibrary.ThemeHelper;
import cntv.themelibrary.Themeable;

/**
 * Created by limingfei on 2017/12/25.
 */

public class ThemeFragment extends Fragment implements Themeable{

    private ThemeHelper themeHelper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        themeHelper = ThemeHelper.getThemeHelper(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        themeHelper.updateTheme();
        refreshTheme(themeHelper);
    }


    @Override
    public void refreshTheme(ThemeHelper themeHelper) {

    }

    public ThemeHelper getThemeHelper() {
        return themeHelper;
    }

    public int getPrimaryColor() {
        return themeHelper.getPrimaryColor();
    }

    public int getDialogStyle() {
        return themeHelper.getDialogStyle();
    }

    public int getAccentColor() {
        return themeHelper.getAccentColor();
    }

    public Theme getBaseTheme(){ return  themeHelper.getBaseTheme(); }

    public int getBackgroundColor(){
        return themeHelper.getBackgroundColor();
    }

    public int getCardBackgroundColor(){
        return themeHelper.getCardBackgroundColor();
    }

    public int getIconColor(){
        return themeHelper.getIconColor();
    }

    public int getTextColor(){
        return themeHelper.getTextColor();
    }
}
