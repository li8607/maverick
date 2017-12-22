package cntv.themelibrary.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ScrollView;

import cntv.themelibrary.ThemeHelper;
import cntv.themelibrary.Themeable;


/**
 * Created by darken (darken@darken.eu) on 04.03.2017.
 */
public class ThemeableScrollView extends ScrollView implements Themeable {
    public ThemeableScrollView(Context context) {
        this(context, null);
    }

    public ThemeableScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemeableScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        themeHelper.setScrollViewColor(this);
    }
}
