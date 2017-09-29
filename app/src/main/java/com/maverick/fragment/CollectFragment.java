package com.maverick.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.maverick.R;
import com.maverick.adapter.CollectFragmentAdapter;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.CollectTabInfo;
import com.maverick.presenter.BasePresenter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by limingfei on 2017/9/29.
 */
public class CollectFragment extends BaseFragment2 {

    private static final String KEY_INFOS = "CollectTabInfos";

    private TabLayout tab_layout;
    private ViewPager viewpager;
    private CollectFragmentAdapter mCollectFragmentAdapter;

    public static CollectFragment newInstance(List<CollectTabInfo> list) {

        CollectFragment fragment = new CollectFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_INFOS, (Serializable) list);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void onInitView(View view) {
        tab_layout = findView(R.id.tab_layout);
        viewpager = findView(R.id.viewpager);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mOnCollectFragmentListener != null) {
                    mOnCollectFragmentListener.onPageSelected();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        List<CollectTabInfo> list = (List<CollectTabInfo>) getArguments().getSerializable(KEY_INFOS);
        mCollectFragmentAdapter = new CollectFragmentAdapter(getChildFragmentManager(), list);
        mCollectFragmentAdapter.setOnCollectFragmentListener(mOnCollectFragmentListener);
        mCollectFragmentAdapter.setOnCollectItemFragmentListener(new CollectItemFragment.OnCollectItemFragmentListener() {
            @Override
            public void onCheckState(int checkState) {
                if(mOnCollectFragmentListener != null) {
                    mOnCollectFragmentListener.onCheckState(checkState);
                }
            }
        });
        viewpager.setAdapter(mCollectFragmentAdapter);
        tab_layout.setupWithViewPager(viewpager);
    }

    public int getStateEdit() {
        Fragment fragment = (Fragment) viewpager.getAdapter().instantiateItem(viewpager, viewpager.getCurrentItem());
        if (fragment != null) {
            if (fragment instanceof CollectFragment) {
                CollectFragment collectFragment = (CollectFragment) fragment;
                return collectFragment.getStateEdit();
            } else if (fragment instanceof CollectItemFragment) {
                CollectItemFragment collectItemFragment = (CollectItemFragment) fragment;
                return collectItemFragment.getStateEdit();
            }
        }
        return -1;
    }

    public void setStateEdit(int stateEdit) {
        Fragment fragment = (Fragment) viewpager.getAdapter().instantiateItem(viewpager, viewpager.getCurrentItem());
        if (fragment != null) {
            if (fragment instanceof CollectFragment) {
                CollectFragment collectFragment = (CollectFragment) fragment;
                collectFragment.setStateEdit(stateEdit);
            } else if (fragment instanceof CollectItemFragment) {
                CollectItemFragment collectItemFragment = (CollectItemFragment) fragment;
                collectItemFragment.setStateEdit(stateEdit);
            }
        }
    }

    public void delete() {
        Fragment fragment = (Fragment) viewpager.getAdapter().instantiateItem(viewpager, viewpager.getCurrentItem());
        if (fragment != null) {
            if (fragment instanceof CollectFragment) {
                CollectFragment collectFragment = (CollectFragment) fragment;
                collectFragment.delete();
            } else if (fragment instanceof CollectItemFragment) {
                CollectItemFragment collectItemFragment = (CollectItemFragment) fragment;
                collectItemFragment.delete();
            }
        }
    }

    public int getCheckState() {
        Fragment fragment = (Fragment) viewpager.getAdapter().instantiateItem(viewpager, viewpager.getCurrentItem());
        if (fragment != null) {
            if (fragment instanceof CollectFragment) {
                CollectFragment collectFragment = (CollectFragment) fragment;
                return collectFragment.getCheckState();
            } else if (fragment instanceof CollectItemFragment) {
                CollectItemFragment collectItemFragment = (CollectItemFragment) fragment;
                return collectItemFragment.getCheckState();
            }
        }
        return -1;
    }

    public void setCheckState(int checkState) {
        Fragment fragment = (Fragment) viewpager.getAdapter().instantiateItem(viewpager, viewpager.getCurrentItem());
        if (fragment != null) {
            if (fragment instanceof CollectFragment) {
                CollectFragment collectFragment = (CollectFragment) fragment;
                 collectFragment.setCheckState(checkState);
            } else if (fragment instanceof CollectItemFragment) {
                CollectItemFragment collectItemFragment = (CollectItemFragment) fragment;
                 collectItemFragment.setCheckState(checkState);
            }
        }
    }

    private OnCollectFragmentListener mOnCollectFragmentListener;

    public void setOnCollectFragmentListener(OnCollectFragmentListener listener) {
        this.mOnCollectFragmentListener = listener;
    }

    public interface OnCollectFragmentListener {
        void onPageSelected();

        void onCheckState(int checkState);
    }
}
