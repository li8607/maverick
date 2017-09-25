package com.maverick.fragment;

import android.os.Bundle;
import android.view.View;

import com.maverick.R;
import com.maverick.base.BaseFragment2;
import com.maverick.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/9/25.
 */
public class MyFragment extends BaseFragment2 {

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void onInitView(View view) {

    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

    }
}
