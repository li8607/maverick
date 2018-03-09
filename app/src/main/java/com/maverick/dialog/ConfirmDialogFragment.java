package com.maverick.dialog;

import android.os.Bundle;
import android.view.View;

import com.maverick.R;
import com.maverick.base.BaseDialogFragment;
import com.maverick.presenter.BasePresenter;

/**
 * Created by limingfei on 2018/3/9.
 */

public class ConfirmDialogFragment extends BaseDialogFragment {

    public static BaseDialogFragment newInstance(String title, String message) {
        ConfirmDialogFragment fragment = new ConfirmDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.dialog_confirm;
    }

    @Override
    protected void onInitView(View view) {

    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        if(getArguments() == null) {
            return;
        }

        String title = getArguments().getString("title");
        String message = getArguments().getString("message");
    }
}
