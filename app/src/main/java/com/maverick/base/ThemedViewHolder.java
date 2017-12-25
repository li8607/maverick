package com.maverick.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cntv.themelibrary.Themeable;

/**
 * Created by Administrator on 2017/12/25.
 */

public abstract class ThemedViewHolder extends RecyclerView.ViewHolder implements Themeable {

    public ThemedViewHolder(View view) {
        super(view);
    }
}