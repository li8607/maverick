package com.maverick.base;

import android.os.Bundle;

/**
 * Created by limingfei on 2018/2/26.
 */

public abstract class BaseLazyFragment extends BaseFragment {

    /**
     * 标记已加载完成，只能加载一次
     */
    private boolean hasLoaded = false;

    /**
     * 标记是否已经onCreate
     */
    private boolean isCreated = false;
    /**
     * 界面对于用户是否可见
     */
    private boolean isVisibleToUser = false;

    /**
     * 监听界面是否展示给用户，实现懒加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad(null);
    }


    /**
     * 懒加载方法，获取数据什么的放到这边来使用，在切换到这个界面时才进行网络请求
     */
    private void lazyLoad(Bundle savedInstanceState) {

        //如果该界面不对用户显示、已经加载、fragment还没有创建，
        //三种情况任意一种，不获取数据
        if (!isVisibleToUser || hasLoaded || !isCreated) {
            return;
        }
        lazyInit(savedInstanceState);
        hasLoaded = true;
    }

    /**
     * 懒加载的初始化方法
     */
    public abstract void lazyInit(Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isCreated = false;
        hasLoaded = false;
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        isCreated = true;
        lazyLoad(savedInstanceState);
    }
}
