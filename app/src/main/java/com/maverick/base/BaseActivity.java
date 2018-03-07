package com.maverick.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.maverick.MainApp;
import com.maverick.presenter.BasePresenter;
import com.umeng.analytics.MobclickAgent;

import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Created by limingfei on 2017/9/25.
 */
public abstract class BaseActivity extends SwipeBackActivity {

    private BasePresenter mBasePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        setTheme(MainApp.getInstance().getCustomTheme());
        super.onCreate(savedInstanceState);
//        initSwipeBackFinish();
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
        if (containerViewId == 0 || fragment == null || getSupportFragmentManager() == null || getSupportFragmentManager().isDestroyed())
            return;

        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void showDialogFragment(@Nullable DialogFragment dialogFragment) {
        if (dialogFragment == null || getSupportFragmentManager() == null || getSupportFragmentManager().isDestroyed())
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

    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        if (mBasePresenter != null) {
            mBasePresenter.release();
        }
        super.onDestroy();
    }
}
