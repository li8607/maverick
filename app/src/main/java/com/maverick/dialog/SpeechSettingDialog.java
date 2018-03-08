package com.maverick.dialog;

import android.os.Bundle;
import android.view.View;

import com.maverick.R;
import com.maverick.base.BaseDialogFragment;
import com.maverick.presenter.BasePresenter;

/**
 * Created by maver on 2018/3/8.
 */

public class SpeechSettingDialog extends BaseDialogFragment {

    public static SpeechSettingDialog newInstance() {
        SpeechSettingDialog dialog = new SpeechSettingDialog();
        return dialog;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.dialog_setting_speech;
    }

    @Override
    protected void onInitView(View view) {

    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

    }
}
