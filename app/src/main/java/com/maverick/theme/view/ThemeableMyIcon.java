package com.maverick.theme.view;

import android.content.Context;
import android.util.AttributeSet;

import com.mikepenz.iconics.view.IconicsImageView;

import cntv.themelibrary.ThemeHelper;
import cntv.themelibrary.Themeable;

/**
 * Created by limingfei on 2017/12/25.
 */

public class ThemeableMyIcon extends IconicsImageView implements Themeable {

    public ThemeableMyIcon(Context context) {
        this(context, null);
    }

    public ThemeableMyIcon(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemeableMyIcon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        setColor(themeHelper.getIconColor());
    }
}
