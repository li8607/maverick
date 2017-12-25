package com.maverick.base;

import android.support.v4.app.Fragment;
import android.view.View;

import cntv.themelibrary.Themeable;
import cntv.themelibrary.ViewUtil;

/**
 * Created by limingfei on 2017/12/25.
 */

public class ThemeFragment extends Fragment {

    public ThemeActivity getThemeActivity() {
        if (getActivity() != null && getActivity() instanceof ThemeActivity) {
            return (ThemeActivity) getActivity();
        }
        return null;
    }

    public void updateUiElements() {

        if (getThemeActivity() == null) {
            return;
        }

        for (View view : ViewUtil.getAllChildren(getView())) {
            if (view instanceof Themeable)
                ((Themeable) view).refreshTheme(getThemeActivity().getThemeHelper());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUiElements();
    }
}
