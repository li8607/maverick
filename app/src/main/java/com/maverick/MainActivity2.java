package com.maverick;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.maverick.base.BaseActivity;
import com.maverick.bean.ButtonInfo;
import com.maverick.bean.SisterDetailInfo;
import com.maverick.fragment.BeautyFragment;
import com.maverick.fragment.JokeFragment;
import com.maverick.fragment.MyFragment;
import com.maverick.fragment.SisterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limingfei on 2017/9/25.
 */
public class MainActivity2 extends BaseActivity {

    private RadioGroup radio_group;
    private RadioButton radio_0;
    private RadioButton radio_1;
    private RadioButton radio_2;
    private RadioButton radio_3;

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


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_0:
                        switchFragment(JokeFragment.newInstance());
                        break;
                    case R.id.radio_1:
                        switchFragment(BeautyFragment.newInstance());
                        break;
                    case R.id.radio_2:
                        switchFragment(SisterFragment.newInstance(new SisterDetailInfo()));
                        break;
                    case R.id.radio_3:
                        switchFragment(MyFragment.newInstance());
                        break;
                }
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        List<ButtonInfo> list = new ArrayList<>();
        list.add(getButtonInfo("笑话", R.drawable.ic_menu_gallery));
        list.add(getButtonInfo("美女", R.drawable.ic_menu_camera));
        list.add(getButtonInfo("百思不得姐", R.drawable.ic_menu_send));
        list.add(getButtonInfo("我的", R.drawable.ic_menu_manage));


        for (int i = 0; i < list.size(); i++) {
            ButtonInfo buttonInfo = list.get(i);

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
        switchFragment(JokeFragment.newInstance());
    }

    private void switchFragment(Fragment fragment) {
        replaceFragment(R.id.content, fragment);
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
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        radioButton.setCompoundDrawables(null, drawable, null, null);
    }

    public ButtonInfo getButtonInfo(String name, int iconId) {
        ButtonInfo buttonInfo = new ButtonInfo();
        buttonInfo.setName(name);
        buttonInfo.setIconId(iconId);
        return buttonInfo;
    }
}
