package com.maverick;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.maverick.base.BaseActivity;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.ButtonInfo;
import com.maverick.factory.FragmentFactory;
import com.maverick.type.FragmentType;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limingfei on 2017/9/25.
 */
public class MainActivity2 extends BaseActivity {

    private RadioGroup radio_group;
    private RadioButton radio_0, radio_1, radio_2, radio_3, radio_4;
    private BaseFragment2 fragment_0, fragment_1, fragment_2, fragment_3, fragment_4;
    private TextView mTitle;
    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private View bottom_bar_line;

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

        radio_group = findView(R.id.radio_group);

        bottom_bar_line = findView(R.id.bottom_bar_line);

        radio_0 = findView(R.id.radio_0);
        radio_1 = findView(R.id.radio_1);
        radio_2 = findView(R.id.radio_2);
        radio_3 = findView(R.id.radio_3);
        radio_4 = findView(R.id.radio_4);

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_0:
                        if (fragment_0 == null) {
                            fragment_0 = FragmentFactory.getMainFragment((ButtonInfo) radio_0.getTag());
                        }
                        switchFragment(fragment_0);
                        setSupportActionBarTitle(radio_0.getText());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mAppBarLayout.setElevation(0);
                        }

                        break;
                    case R.id.radio_1:
                        if (fragment_1 == null) {
                            fragment_1 = FragmentFactory.getMainFragment((ButtonInfo) radio_1.getTag());
                        }
                        switchFragment(fragment_1);
                        setSupportActionBarTitle(radio_1.getText());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mAppBarLayout.setElevation(0);
                        }

                        break;
                    case R.id.radio_2:
                        if (fragment_2 == null) {
                            fragment_2 = FragmentFactory.getMainFragment((ButtonInfo) radio_2.getTag());
                        }
                        switchFragment(fragment_2);
                        setSupportActionBarTitle(radio_2.getText());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mAppBarLayout.setElevation(0);
                        }

                        break;
                    case R.id.radio_3:
                        if (fragment_3 == null) {
                            fragment_3 = FragmentFactory.getMainFragment((ButtonInfo) radio_3.getTag());
                        }
                        switchFragment(fragment_3);
                        setSupportActionBarTitle(radio_3.getText());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mAppBarLayout.setElevation(0);
                        }

                        break;
                    case R.id.radio_4:
                        if (fragment_4 == null) {
                            fragment_4 = FragmentFactory.getMainFragment((ButtonInfo) radio_4.getTag());
                        }
                        switchFragment(fragment_4);
                        setSupportActionBarTitle(radio_4.getText());

                        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
                        if (layoutParams != null) {
                            AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) layoutParams.getBehavior();
                            behavior.onNestedFling(mCoordinatorLayout, mAppBarLayout, null, 0, -1000, true);
                        }


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mAppBarLayout.setElevation(getResources().getDimension(R.dimen.card_elevation));
                        }

                        break;

                }
            }
        });

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

    @Override
    public void recreate() {
        if (mFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(mFragment).commitAllowingStateLoss();
        }
        super.recreate();
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        List<ButtonInfo> mList = new ArrayList<>();
        mList.add(getButtonInfo(getString(R.string.fragment_sister), R.drawable.bottom_sister_selector, FragmentType.SISTER));
//        mList.add(getButtonInfo(getString(R.string.fragment_beauty), R.drawable.bottom_beauty_selector, FragmentType.BEAUTY));
        mList.add(getButtonInfo(getString(R.string.fragment_caricature), R.drawable.bottom_caricature_selector, FragmentType.CARICATURE));
//        mList.add(getButtonInfo(getString(R.string.fragment_sina), R.drawable.bottom_sina_selector, FragmentType.SINA));
        mList.add(getButtonInfo(getString(R.string.fragment_pear), R.drawable.bottom_sina_selector, FragmentType.PEAR));
        mList.add(getButtonInfo(getString(R.string.fragment_joke), R.drawable.bottom_joke_selector, FragmentType.JOKE));
        mList.add(getButtonInfo(getString(R.string.fragment_my), R.drawable.bottom_my_selector, FragmentType.MY));

        for (int i = 0; i < mList.size(); i++) {
            ButtonInfo buttonInfo = mList.get(i);

            switch (i) {
                case 0:
                    setRadioButtonData(radio_0, buttonInfo);
                    break;
                case 1:
                    setRadioButtonData(radio_1, buttonInfo);
                    break;
                case 2:
                    setRadioButtonData(radio_2, buttonInfo);
                    break;
                case 3:
                    setRadioButtonData(radio_3, buttonInfo);
                    break;
                case 4:
                    setRadioButtonData(radio_4, buttonInfo);
                    break;
            }
        }

        radio_0.setChecked(true);
        if (fragment_0 == null) {
            fragment_0 = FragmentFactory.getMainFragment((ButtonInfo) radio_0.getTag());
        }
        switchFragment(fragment_0);
    }

    private BaseFragment2 mFragment;

    private void switchFragment(BaseFragment2 fragment) {

        if (fragment == null || fragment.isVisible() || fragment.equals(mFragment)) {
            return;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mFragment != null && mFragment.isAdded()) {
            transaction.hide(mFragment);
        }

        if (!fragment.isAdded()) {
            transaction.add(R.id.content, fragment, BaseFragment2.class.getSimpleName());
        } else {
            transaction.show(fragment);
        }

        transaction.commitAllowingStateLoss();
        this.mFragment = fragment;
    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void setRadioButtonData(RadioButton radioButton, ButtonInfo buttonInfo) {

        radioButton.setButtonDrawable(null);
        radioButton.setText(buttonInfo.getName());
        radioButton.setGravity(Gravity.CENTER);

        radioButton.setTextColor(ContextCompat.getColorStateList(MainActivity2.this, R.color.selector_radiobutton_text_color_main));

        Drawable drawable;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = getDrawable(buttonInfo.getIconId());
        } else {
            drawable = getResources().getDrawable(buttonInfo.getIconId());
        }
        drawable.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.x16), getResources().getDimensionPixelSize(R.dimen.x16));
        radioButton.setCompoundDrawables(null, drawable, null, null);
        radioButton.setTag(buttonInfo);
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
        if (mFragment != null && mFragment.onBackPressed()) {
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
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
