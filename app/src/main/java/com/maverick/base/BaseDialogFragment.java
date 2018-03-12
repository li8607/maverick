package com.maverick.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.maverick.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/9/25.
 */
public abstract class BaseDialogFragment extends AppCompatDialogFragment {

    private BasePresenter mBasePresenter;
    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1 通过样式定义
        this.mBasePresenter = this.onCreatePresenter();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                if (layoutParams != null) {
                    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                }
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        @LayoutRes int layoutResID = getRootViewId();
        if (layoutResID != 0) {
            mView = inflater.inflate(layoutResID, container, true);
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
        if (containerViewId == 0 || fragment == null || getChildFragmentManager() == null || getChildFragmentManager().isDestroyed())
            return;

        FragmentTransaction fragmentTransaction = this.getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public boolean onBackPressed() {
        return false;
    }
}
