package com.maverick;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.maverick.base.BaseActivity;
import com.maverick.base.BaseFragment;
import com.maverick.bean.CollectTabInfo;
import com.maverick.fragment.BrowsingHistoryFragment;
import com.maverick.fragment.CollectFragment;
import com.maverick.fragment.MainFragment;
import com.maverick.fragment.PearFragment;
import com.maverick.fragment.SisterFragment;
import com.maverick.global.ActivityCode;
import com.maverick.type.FragmentType;
import com.umeng.socialize.UMShareAPI;

import me.yokeyword.fragmentation.SwipeBackLayout;

/**
 * Created by limingfei on 2017/9/25.
 */
public class MainActivity2 extends BaseActivity {

    private Toolbar mToolbar;
    private MainFragment mMainFragment;
    private SisterFragment mSisterFragment;
    private PearFragment mPearFragment;
    private BrowsingHistoryFragment mBrowsingHistoryFragment;
    private CollectFragment mCollectFragment;
    private Fragment mCurrentFragment;

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
        // 设置滑动方向
        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.STATE_IDLE); // EDGE_LEFT(默认),EDGE_ALL

        mToolbar = findView(R.id.toolbar_actionbar);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(mPermissionList, 123);
            }
        }

        DrawerLayout drawer = (DrawerLayout) MainActivity2.this.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();
                mToolbar.setTitle(item.getTitle());

                if (id == R.id.nav_gif) {
                    if (mMainFragment == null) {
                        mMainFragment = MainFragment.newInstance();
                    }
                    switchContent(mMainFragment);
                    mToolbar.setVisibility(View.GONE);
                } else if (id == R.id.nav_history) {
                    if (mBrowsingHistoryFragment == null) {
                        mBrowsingHistoryFragment = BrowsingHistoryFragment.newInstance();
                    }

                    switchContent(mBrowsingHistoryFragment);
                    mToolbar.setVisibility(View.VISIBLE);
                    setSupportActionBar(mToolbar);
                } else if (id == R.id.nav_collect) {

                    if (mCollectFragment == null) {
                        CollectTabInfo collectTabInfo = new CollectTabInfo();
                        collectTabInfo.setType(FragmentType.COLLECT);
                        mCollectFragment = CollectFragment.newInstance(collectTabInfo);
                    }

                    switchContent(mCollectFragment);
                    mToolbar.setVisibility(View.VISIBLE);
                    setSupportActionBar(mToolbar);
                } else if (id == R.id.nav_share) {

                } else if (id == R.id.nav_send) {

                }

                DrawerLayout drawer = (DrawerLayout) MainActivity2.this.findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if (mMainFragment == null) {
                mMainFragment = MainFragment.newInstance();
            }
            ft.replace(R.id.fl_content, mMainFragment).setPrimaryNavigationFragment(mMainFragment).commit();
            mCurrentFragment = mMainFragment;
        }
    }

    private long currentTime;

    @Override
    public void onBackPressedSupport() {
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

    /**
     * 当fragment进行切换时，采用隐藏与显示的方法加载fragment以防止数据的重复加载
     *
     * @param to
     */
    public void switchContent(Fragment to) {
        if (getSupportFragmentManager().getPrimaryNavigationFragment() != to) {
            FragmentManager fm = getSupportFragmentManager();
            //添加渐隐渐现的动画
            FragmentTransaction ft = fm.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                ft.hide(getSupportFragmentManager().getPrimaryNavigationFragment()).add(R.id.fl_content, to).setPrimaryNavigationFragment(to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                ft.hide(getSupportFragmentManager().getPrimaryNavigationFragment()).show(to).setPrimaryNavigationFragment(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
}
