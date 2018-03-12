package com.maverick.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.adapter.MainActivityAdapter;
import com.maverick.base.BaseFragment;
import com.maverick.bean.ButtonInfo;
import com.maverick.presenter.BasePresenter;
import com.maverick.type.FragmentType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limingfei on 2018/3/12.
 */

public class MainFragment extends BaseFragment {

    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewPager;
    private List<ButtonInfo> mList;
    private MainActivityAdapter mMainActivityAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_sister:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_caricature:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_pear:
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_joke:
                    mViewPager.setCurrentItem(3);
                    return true;
                case R.id.navigation_my:
                    mViewPager.setCurrentItem(4);
                    return true;
            }
            return false;
        }
    };
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private AppBarLayout.Behavior mBehavior;
    private AppCompatActivity mActivity;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_main;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void onInitView(View view) {

        mActivity = ((AppCompatActivity) getActivity());

        mAppBarLayout = view.findViewById(R.id.appbar);

        Toolbar mToolbar = findView(R.id.toolbar_actionbar);
        mTitle = new TextView(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTitle.setTextAppearance(android.R.style.TextAppearance_Material_Widget_ActionBar_Title);
        } else {
            mTitle.setTextAppearance(getContext(), android.R.style.TextAppearance_Material_Widget_ActionBar_Title);
        }
        mTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.textColorPrimary));
        Toolbar.LayoutParams mTitleLP = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTitleLP.gravity = Gravity.CENTER;
        mTitle.setVisibility(View.GONE);
        mToolbar.setTitle("");
        mToolbar.addView(mTitle, mTitleLP);
        mActivity.setSupportActionBar(mToolbar);

        mBottomNavigationView = (BottomNavigationView) view.findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPager = view.findViewById(R.id.vp_main);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSupportActionBarTitle(mList.get(position).getName());

                // 将当前的页面对应的底部标签设为选中状态
                mBottomNavigationView.getMenu().getItem(position).setChecked(true);

                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
                if (mBehavior != null) {
                    layoutParams.setBehavior(mBehavior);
                }

                if (mList.get(position).getType() == FragmentType.MY) {
                    mBehavior = (AppBarLayout.Behavior) layoutParams.getBehavior();
                    if (mBehavior != null) {
                        mBehavior.setTopAndBottomOffset(0);
                    }
                    layoutParams.setBehavior(null);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mAppBarLayout.setElevation(getResources().getDimension(R.dimen.card_elevation));
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mAppBarLayout.setElevation(0);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        mList.add(getButtonInfo(getString(R.string.fragment_sister), FragmentType.SISTER));
        mList.add(getButtonInfo(getString(R.string.fragment_caricature), FragmentType.CARICATURE));
//        mList.add(getButtonInfo(getString(R.string.fragment_beauty),  FragmentType.BEAUTY));
//        mList.add(getButtonInfo(getString(R.string.fragment_sina),  FragmentType.SINA));
        mList.add(getButtonInfo(getString(R.string.fragment_pear), FragmentType.PEAR));
        mList.add(getButtonInfo(getString(R.string.fragment_joke), FragmentType.JOKE));
        mList.add(getButtonInfo(getString(R.string.fragment_my), FragmentType.MY));

        mMainActivityAdapter = new MainActivityAdapter(getChildFragmentManager());
        mMainActivityAdapter.setList(mList);
        mViewPager.setAdapter(mMainActivityAdapter);

        setSupportActionBarTitle(mList.get(0).getName());
    }

    public ButtonInfo getButtonInfo(String name, int type) {
        ButtonInfo buttonInfo = new ButtonInfo();
        buttonInfo.setName(name);
        buttonInfo.setType(type);
        return buttonInfo;
    }

    public void setSupportActionBarTitle(CharSequence title) {
        if (mActivity.getSupportActionBar() == null || TextUtils.isEmpty(title)) {
            return;
        }
        mActivity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        mActivity.getSupportActionBar().setTitle("");
        mTitle.setText(title);
        mTitle.setVisibility(View.VISIBLE);
    }

}
