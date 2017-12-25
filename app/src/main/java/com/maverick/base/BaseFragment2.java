package com.maverick.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.presenter.BasePresenter;
import com.umeng.analytics.MobclickAgent;

import cntv.themelibrary.Themeable;
import cntv.themelibrary.ViewUtil;

/**
 * Created by Administrator on 2017/9/25.
 */
public abstract class BaseFragment2 extends ThemeFragment {

    private BasePresenter mBasePresenter;
    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mBasePresenter = this.onCreatePresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @LayoutRes int layoutResID = getRootViewId();
        mView = null;
        if (layoutResID != 0) {
            mView = inflater.inflate(layoutResID, container, false);
        }
        onInitView(mView);
        onInitData(savedInstanceState);
        return mView;
    }

    @Override
    public void onDestroyView() {
        if (mBasePresenter != null) {
            mBasePresenter.release();
        }
        super.onDestroyView();
    }

    protected abstract BasePresenter onCreatePresenter();

    protected abstract int getRootViewId();

    protected abstract void onInitView(View view);

    protected abstract void onInitData(Bundle savedInstanceState);

    public <T extends View> T findView(int id) {
        return (T) mView.findViewById(id);
    }

    public <T extends View> T findView(View view, int id) {
        return (T) view.findViewById(id);
    }

    public void replaceFragment(@IdRes int containerViewId, @Nullable Fragment fragment) {
        if (containerViewId == 0 || fragment == null || getChildFragmentManager() == null)
            return;

        FragmentTransaction fragmentTransaction = this.getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void showDialogFragment(@Nullable DialogFragment dialogFragment) {
        if (dialogFragment == null || getChildFragmentManager() == null)
            return;

        FragmentTransaction fragmentTransaction = this.getChildFragmentManager().beginTransaction();

        String tag = dialogFragment.getClass().getName();
        Fragment fragment = this.getChildFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.add(dialogFragment, tag);
        fragmentTransaction.commitAllowingStateLoss();//注意这里使用commitAllowingStateLoss()
        //Fragment Or DialogFragment Can not perform this action after onSaveInstanceState
    }

    public boolean onBackPressed() {
        return false;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName()); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

}
