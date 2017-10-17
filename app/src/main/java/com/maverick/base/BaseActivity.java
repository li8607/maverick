package com.maverick.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.maverick.presenter.BasePresenter;

/**
 * Created by limingfei on 2017/9/25.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private BasePresenter mBasePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 第一种方法
        super.onCreate(savedInstanceState);
        onActivityCreated(savedInstanceState);
    }

    private void onActivityCreated(Bundle savedInstanceState) {
        @LayoutRes int layoutResID = getRootViewId();
        if (layoutResID != 0) {
            this.setContentView(layoutResID);
        }

        this.mBasePresenter = this.onCreatePresenter();
        this.onInitView();
        this.onInitData(savedInstanceState);
    }

    protected abstract BasePresenter onCreatePresenter();

    protected abstract int getRootViewId();

    protected abstract void onInitView();

    protected abstract void onInitData(Bundle savedInstanceState);

    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    public <T extends View> T findView(View view, int id) {
        return (T) view.findViewById(id);
    }

    public void replaceFragment(@IdRes int containerViewId, @Nullable Fragment fragment) {
        if (containerViewId == 0 || fragment == null || getSupportFragmentManager() == null)
            return;

        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void showDialogFragment(@Nullable DialogFragment dialogFragment) {
        if (dialogFragment == null || getSupportFragmentManager() == null)
            return;

        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();

        String tag = dialogFragment.getClass().getName();
        Fragment fragment = this.getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.add(dialogFragment, tag);
        fragmentTransaction.commitAllowingStateLoss();//注意这里使用commitAllowingStateLoss()
        //Fragment Or DialogFragment Can not perform this action after onSaveInstanceState
    }
}
