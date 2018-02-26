package com.maverick;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.maverick.base.BaseActivity;
import com.maverick.base.BaseFragment;
import com.maverick.bean.ButtonInfo;
import com.maverick.factory.FragmentFactory;
import com.maverick.fragment.CaricatureFragment;
import com.maverick.fragment.JokeFragment;
import com.maverick.fragment.MyFragment;
import com.maverick.fragment.PearFragment;
import com.maverick.fragment.SisterFragment;
import com.maverick.global.ActivityCode;
import com.maverick.type.FragmentType;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limingfei on 2017/9/25.
 */
public class MainActivity2 extends BaseActivity {

    private BaseFragment fragment_0, fragment_1, fragment_2, fragment_3, fragment_4;
    private TextView mTitle;
    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.e("lmf", "item = " + item.getTitle());

            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
            if (mBehavior != null) {
                layoutParams.setBehavior(mBehavior);
            }

            switch (item.getItemId()) {
                case R.id.navigation_sister:
                    if (fragment_0 == null) {
                        fragment_0 = FragmentFactory.getMainFragment(mList.get(0));
                    }
                    switchFragment(fragment_0);
                    setSupportActionBarTitle(item.getTitle());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mAppBarLayout.setElevation(0);
                    }
                    return true;
                case R.id.navigation_caricature:
                    if (fragment_1 == null) {
                        fragment_1 = FragmentFactory.getMainFragment(mList.get(1));
                    }
                    switchFragment(fragment_1);
                    setSupportActionBarTitle(item.getTitle());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mAppBarLayout.setElevation(0);
                    }
                    return true;
                case R.id.navigation_pear:
                    if (fragment_2 == null) {
                        fragment_2 = FragmentFactory.getMainFragment(mList.get(2));
                    }
                    switchFragment(fragment_2);
                    setSupportActionBarTitle(item.getTitle());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mAppBarLayout.setElevation(0);
                    }
                    return true;
                case R.id.navigation_joke:
                    if (fragment_3 == null) {
                        fragment_3 = FragmentFactory.getMainFragment(mList.get(3));
                    }
                    switchFragment(fragment_3);
                    setSupportActionBarTitle(item.getTitle());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mAppBarLayout.setElevation(0);
                    }

                    return true;
                case R.id.navigation_my:
                    if (fragment_4 == null) {
                        fragment_4 = FragmentFactory.getMainFragment(mList.get(4));
                    }
                    switchFragment(fragment_4);
                    setSupportActionBarTitle(item.getTitle());

                    mBehavior = (AppBarLayout.Behavior) layoutParams.getBehavior();
                    if (mBehavior != null) {
                        mBehavior.setTopAndBottomOffset(0);
                    }
                    layoutParams.setBehavior(null);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mAppBarLayout.setElevation(getResources().getDimension(R.dimen.card_elevation));
                    }
                    return true;
            }
            return false;
        }
    };
    private List<ButtonInfo> mList;
    private BottomNavigationView mBottomNavigationView;
    private AppBarLayout.Behavior mBehavior;

    @Override
    protected com.maverick.presenter.BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void onInitView() {
        mCoordinatorLayout = findView(R.id.coordinatorLayout);
        mAppBarLayout = findView(R.id.appbar);

        mToolbar = findView(R.id.toolbar_actionbar);
        mTitle = new TextView(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTitle.setTextAppearance(android.R.style.TextAppearance_Material_Widget_ActionBar_Title);
        } else {
            mTitle.setTextAppearance(MainActivity2.this, android.R.style.TextAppearance_Material_Widget_ActionBar_Title);
        }
        mTitle.setTextColor(ContextCompat.getColor(MainActivity2.this, R.color.textColorPrimary));
        Toolbar.LayoutParams mTitleLP = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTitleLP.gravity = Gravity.CENTER;
        mTitle.setVisibility(View.GONE);
        mToolbar.addView(mTitle, mTitleLP);
        setSupportActionBar(mToolbar);

        mBottomNavigationView = findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(mPermissionList, 123);
            }
        }
    }

    public void setSupportActionBarTitle(CharSequence title) {
        if (getSupportActionBar() == null || TextUtils.isEmpty(title)) {
            return;
        }
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("");
        mTitle.setText(title);
        mTitle.setVisibility(View.VISIBLE);
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
//        if (layoutParams != null) {
//            AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) layoutParams.getBehavior();
//            outState.putParcelable("behavior_state", behavior.onSaveInstanceState(mCoordinatorLayout, mAppBarLayout));
//        }
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        Parcelable parcelable = savedInstanceState.getParcelable("behavior_state");
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
//        if (layoutParams != null && parcelable != null) {
//            AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) layoutParams.getBehavior();
//            if (behavior != null) {
//                behavior.onRestoreInstanceState(mCoordinatorLayout, mAppBarLayout, parcelable);
//            }
//        }
//    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        mList = new ArrayList<>();
        mList.add(getButtonInfo(getString(R.string.fragment_sister), 0, FragmentType.SISTER));
//        mList.add(getButtonInfo(getString(R.string.fragment_beauty), 0, FragmentType.BEAUTY));
        mList.add(getButtonInfo(getString(R.string.fragment_caricature), 0, FragmentType.CARICATURE));
//        mList.add(getButtonInfo(getString(R.string.fragment_sina), 0, FragmentType.SINA));
        mList.add(getButtonInfo(getString(R.string.fragment_pear), 0, FragmentType.PEAR));
        mList.add(getButtonInfo(getString(R.string.fragment_joke), 0, FragmentType.JOKE));
        mList.add(getButtonInfo(getString(R.string.fragment_my), 0, FragmentType.MY));

        if (savedInstanceState != null) {
            fragment_0 = (BaseFragment) getSupportFragmentManager().findFragmentByTag(SisterFragment.class.getName());
            fragment_1 = (BaseFragment) getSupportFragmentManager().findFragmentByTag(CaricatureFragment.class.getName());
            fragment_2 = (BaseFragment) getSupportFragmentManager().findFragmentByTag(PearFragment.class.getName());
            fragment_3 = (BaseFragment) getSupportFragmentManager().findFragmentByTag(JokeFragment.class.getName());
            fragment_4 = (BaseFragment) getSupportFragmentManager().findFragmentByTag(MyFragment.class.getName());
        } else {
            mBottomNavigationView.setSelectedItemId(R.id.navigation_sister);
        }
    }

    private BaseFragment mFragment;

    private void switchFragment(BaseFragment fragment) {

        if (fragment == null || fragment.isVisible() || getSupportFragmentManager() == null || fragment.equals(getSupportFragmentManager().getPrimaryNavigationFragment())) {
            return;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (getSupportFragmentManager().getPrimaryNavigationFragment() != null) {
            transaction.hide(getSupportFragmentManager().getPrimaryNavigationFragment());
        }

        if (!fragment.isAdded()) {
            transaction.add(R.id.content, fragment, fragment.getClass().getName());
        } else {
            transaction.show(fragment);
        }

        transaction.setPrimaryNavigationFragment(fragment);
        this.mFragment = fragment;
        transaction.commitAllowingStateLoss();
    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public ButtonInfo getButtonInfo(String name, int iconId, int type) {
        ButtonInfo buttonInfo = new ButtonInfo();
        buttonInfo.setName(name);
        buttonInfo.setIconId(iconId);
        buttonInfo.setType(type);

        return buttonInfo;
    }

    private long currentTime;

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getPrimaryNavigationFragment() != null && ((BaseFragment) (getSupportFragmentManager().getPrimaryNavigationFragment())).onBackPressed()) {
            return;
        }

        if ((System.currentTimeMillis() - currentTime) > 2000) {
            currentTime = System.currentTimeMillis();
            Toast.makeText(this, R.string.exit_app, Toast.LENGTH_SHORT).show();
            return;
        } else {
            finish();
        }

        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ActivityCode.RESULT_CODE_THEME && requestCode == ActivityCode.REQUEST_CODE_THEME) {
            recreate();
        }
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
