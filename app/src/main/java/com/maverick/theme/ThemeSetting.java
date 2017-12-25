package com.maverick.theme;

import com.maverick.base.ThemeActivity;

import cntv.themelibrary.PreferenceUtil;

/**
 * Created by limingfei on 2017/12/25.
 */

public class ThemeSetting {

    private ThemeActivity activity;
    private PreferenceUtil SP;

    public ThemeSetting(ThemeActivity activity) {
        this.activity = activity;
        if (activity != null) {
            SP = PreferenceUtil.getInstance(activity);
        }
    }

    public ThemeActivity getActivity() {
        return activity;
    }

    public void setActivity(ThemeActivity activity) {
        this.activity = activity;
    }

    public PreferenceUtil getSP() {
        return SP;
    }

    public void setSP(PreferenceUtil SP) {
        this.SP = SP;
    }
}
