package com.maverick.theme.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import cntv.themelibrary.ThemeHelper;
import cntv.themelibrary.Themeable;

/**
 * Created by Administrator on 2017/12/25.
 */

public class ThemeableMyLine extends View implements Themeable {

    public ThemeableMyLine(Context context) {
        this(context, null);
    }

    public ThemeableMyLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemeableMyLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        setBackgroundColor(themeHelper.getTextColor());
    }
}
