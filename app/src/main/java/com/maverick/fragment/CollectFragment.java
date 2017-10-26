package com.maverick.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.maverick.R;
import com.maverick.adapter.CollectFragmentAdapter;
import com.maverick.bean.CollectTabInfo;
import com.maverick.model.CollectModel;
import com.maverick.presenter.BasePresenter;
import com.maverick.transformer.DepthPageTransformer;
import com.maverick.type.FragmentType;

import java.util.ArrayList;
import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/9/29.
 */
public class CollectFragment extends BaseEditFragment {

    private static final String KEY_INFOS = "CollectTabInfos";

    private TabLayout tab_layout;
    private ViewPager viewpager;
    private CollectFragmentAdapter mCollectFragmentAdapter;

    public static CollectFragment newInstance(CollectTabInfo info) {

        CollectFragment fragment = new CollectFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_INFOS, info);
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
        viewpager.setPageTransformer(true, new DepthPageTransformer());
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
        CollectTabInfo info = (CollectTabInfo) getArguments().getSerializable(KEY_INFOS);

        if (info == null) {
            return;
        }
        List<CollectTabInfo> list = new ArrayList<>();
        switch (info.getType()) {
            case FragmentType.COLLECT:
                List<Collect> sisterData = CollectModel.newInstance().getSisterData();
                if (sisterData != null && sisterData.size() > 0) {
                    list.add(getCollectTabInfo(getString(R.string.fragment_sister), FragmentType.COLLECT_SISTER));
                }

                List<Collect> caricatureData = CollectModel.newInstance().getCaricatureData();
                if (caricatureData != null && caricatureData.size() > 0) {
                    list.add(getCollectTabInfo(getString(R.string.fragment_caricature), FragmentType.COLLECT_CARICATURE));
                }

                List<Collect> sinaData = CollectModel.newInstance().getSinaData();
                if (sinaData != null && sinaData.size() > 0) {
                    list.add(getCollectTabInfo(getString(R.string.fragment_sina), FragmentType.COLLECT_SINA));
                }

                break;
        }

        if (list == null || list.size() < 1) {
            Toast.makeText(getContext(), "暂无数据", Toast.LENGTH_SHORT).show();
            return;
        }

        mCollectFragmentAdapter = new CollectFragmentAdapter(getChildFragmentManager(), list);
        mCollectFragmentAdapter.setOnCollectFragmentListener(mOnCollectFragmentListener);
        mCollectFragmentAdapter.setOnBaseEditFragmentListener(mOnBaseEditFragmentListener);
        viewpager.setAdapter(mCollectFragmentAdapter);
        tab_layout.setupWithViewPager(viewpager);
    }

    private CollectTabInfo getCollectTabInfo(String title, int type) {
        CollectTabInfo collectTabInfo = new CollectTabInfo();
        collectTabInfo.setTitle(title);
        collectTabInfo.setType(type);
        return collectTabInfo;
    }

    public int getStateEdit() {
        if (viewpager.getAdapter() != null && viewpager.getAdapter().getCount() > viewpager.getCurrentItem()) {
            Fragment fragment = (Fragment) viewpager.getAdapter().instantiateItem(viewpager, viewpager.getCurrentItem());
            if (fragment != null) {
                if (fragment instanceof BaseEditFragment) {
                    BaseEditFragment baseEditFragment = (BaseEditFragment) fragment;
                    return baseEditFragment.getStateEdit();
                }
            }
        }
        return -1;
    }

    public void setStateEdit(int stateEdit) {
        if (viewpager.getAdapter() != null && viewpager.getAdapter().getCount() > viewpager.getCurrentItem()) {
            Fragment fragment = (Fragment) viewpager.getAdapter().instantiateItem(viewpager, viewpager.getCurrentItem());
            if (fragment != null) {
                if (fragment instanceof BaseEditFragment) {
                    BaseEditFragment baseEditFragment = (BaseEditFragment) fragment;
                    baseEditFragment.setStateEdit(stateEdit);
                }
            }
        }
    }

    public void delete() {
        if (viewpager.getAdapter() != null && viewpager.getAdapter().getCount() > viewpager.getCurrentItem()) {
            Fragment fragment = (Fragment) viewpager.getAdapter().instantiateItem(viewpager, viewpager.getCurrentItem());
            if (fragment != null) {
                if (fragment instanceof BaseEditFragment) {
                    BaseEditFragment baseEditFragment = (BaseEditFragment) fragment;
                    baseEditFragment.delete();
                }
            }
        }
    }

    public int getCheckState() {
        if (viewpager.getAdapter() != null && viewpager.getAdapter().getCount() > viewpager.getCurrentItem()) {
            Fragment fragment = (Fragment) viewpager.getAdapter().instantiateItem(viewpager, viewpager.getCurrentItem());
            if (fragment != null) {
                if (fragment instanceof BaseEditFragment) {
                    BaseEditFragment baseEditFragment = (BaseEditFragment) fragment;
                    return baseEditFragment.getCheckState();
                }
            }
        }
        return -1;
    }

    public void setCheckState(int checkState) {
        if (viewpager.getAdapter() != null && viewpager.getAdapter().getCount() > viewpager.getCurrentItem()) {
            Fragment fragment = (Fragment) viewpager.getAdapter().instantiateItem(viewpager, viewpager.getCurrentItem());
            if (fragment != null) {
                if (fragment instanceof BaseEditFragment) {
                    BaseEditFragment baseEditFragment = (BaseEditFragment) fragment;
                    baseEditFragment.setCheckState(checkState);
                }
            }
        }
    }

    private OnCollectFragmentListener mOnCollectFragmentListener;

    public void setOnCollectFragmentListener(OnCollectFragmentListener listener) {
        this.mOnCollectFragmentListener = listener;
    }

    public interface OnCollectFragmentListener {
        void onPageSelected();
    }
}
