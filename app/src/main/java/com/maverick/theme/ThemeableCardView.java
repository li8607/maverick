package com.maverick.theme;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import cntv.themelibrary.ThemeHelper;
import cntv.themelibrary.Themeable;

/**
 * Created by limingfei on 2017/12/25.
 */

public class ThemeableCardView extends CardView implements Themeable {
    public ThemeableCardView(Context context) {
        this(context, null);
    }

    public ThemeableCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemeableCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        setBackgroundColor(themeHelper.getCardBackgroundColor());
    }
}
