package com.maverick.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by limingfei on 2018/3/5.
 */

public class ThemeBroadcastReceiver extends BroadcastReceiver {

    private OnThemeChangeListener mOnThemeChangeListener;

    public ThemeBroadcastReceiver(OnThemeChangeListener listener) {
        this.mOnThemeChangeListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mOnThemeChangeListener != null) {
            mOnThemeChangeListener.onThemeChange();
        }
    }

    public interface OnThemeChangeListener {
        void onThemeChange();
    }
}
