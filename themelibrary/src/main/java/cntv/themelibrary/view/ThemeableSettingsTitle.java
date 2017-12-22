package cntv.themelibrary.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import cntv.themelibrary.ThemeHelper;
import cntv.themelibrary.Themeable;


/**
 * Created by darken (darken@darken.eu) on 04.03.2017.
 */
public class ThemeableSettingsTitle extends android.support.v7.widget.AppCompatTextView implements Themeable {
    public ThemeableSettingsTitle(Context context) {
        this(context, null);
    }

    public ThemeableSettingsTitle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemeableSettingsTitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        setTextColor(themeHelper.getTextColor());
    }
}
