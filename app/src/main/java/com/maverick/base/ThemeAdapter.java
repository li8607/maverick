package com.maverick.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import cntv.themelibrary.ThemeHelper;
import cntv.themelibrary.Themeable;

/**
 * Created by Administrator on 2017/12/25.
 */

public abstract class ThemeAdapter<VH extends ThemedViewHolder> extends RecyclerView.Adapter<VH> implements Themeable {

    private ThemeHelper themeHelper;

    public ThemeAdapter(Context context) {
        themeHelper = ThemeHelper.getThemeHelper(context);
    }

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        this.themeHelper = themeHelper;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.refreshTheme(themeHelper);
    }

    public ThemeHelper getThemeHelper() {
        return themeHelper;
    }
}
