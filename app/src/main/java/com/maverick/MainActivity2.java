package com.maverick;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
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
    private RadioButton radio_0, radio_1, radio_2, radio_3;
    private BaseFragment2 fragment_0, fragment_1, fragment_2, fragment_3;
    private TextView title;

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
        radio_group = findView(R.id.radio_group);

        radio_0 = findView(R.id.radio_0);
        radio_1 = findView(R.id.radio_1);
        radio_2 = findView(R.id.radio_2);
        radio_3 = findView(R.id.radio_3);

        title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_0:
                        if (fragment_0 == null) {
                            fragment_0 = FragmentFactory.getMainFragment((ButtonInfo) radio_0.getTag());
                        }
                        switchFragment(fragment_0);
                        title.setText(radio_0.getText());
                        break;
                    case R.id.radio_1:
                        if (fragment_1 == null) {
                            fragment_1 = FragmentFactory.getMainFragment((ButtonInfo) radio_1.getTag());
                        }
                        switchFragment(fragment_1);
                        title.setText(radio_1.getText());
                        break;
                    case R.id.radio_2:
                        if (fragment_2 == null) {
                            fragment_2 = FragmentFactory.getMainFragment((ButtonInfo) radio_2.getTag());
                        }
                        switchFragment(fragment_2);
                        title.setText(radio_2.getText());
                        break;
                    case R.id.radio_3:
                        if (fragment_3 == null) {
                            fragment_3 = FragmentFactory.getMainFragment((ButtonInfo) radio_3.getTag());
                        }
                        switchFragment(fragment_3);
                        title.setText(radio_3.getText());
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

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        List<ButtonInfo> mList = new ArrayList<>();
        mList.add(getButtonInfo("百思不得姐", R.drawable.bottom_sister_selector, FragmentType.SISTER));
        mList.add(getButtonInfo("美女", R.drawable.bottom_beauty_selector, FragmentType.BEAUTY));
        mList.add(getButtonInfo("笑话", R.drawable.bottom_joke_selector, FragmentType.JOKE));
        mList.add(getButtonInfo("我的", R.drawable.bottom_my_selector, FragmentType.MY));

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
            transaction.add(R.id.content, fragment, "");
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            radioButton.setTextColor(getColorStateList(R.color.selector_radiobutton_text_color_main));
        } else {
            radioButton.setTextColor(getResources().getColorStateList(R.color.selector_radiobutton_text_color_main));
        }

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

    @Override
    public void onBackPressed() {
        if (mFragment != null && mFragment.onBackPressed()) {
            return;
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
