package com.maverick.theme;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import cntv.themelibrary.ThemeHelper;
import cntv.themelibrary.Themeable;

/**
 * Created by limingfei on 2017/12/25.
 */

public class ThemeableSettingsCategory extends AppCompatTextView implements Themeable {
    public ThemeableSettingsCategory(Context context) {
        this(context, null);
    }

    public ThemeableSettingsCategory(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemeableSettingsCategory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        themeHelper.setTextViewColor(this, themeHelper.getAccentColor());
    }
}
