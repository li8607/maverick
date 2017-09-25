package com.maverick;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.maverick.base.BaseActivity;
import com.maverick.bean.ButtonInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limingfei on 2017/9/25.
 */
public class MainActivity2 extends BaseActivity {

    private RadioGroup radio_group;

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
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        List<ButtonInfo> list = new ArrayList<>();
        list.add(getButtonInfo("文本笑话", R.drawable.ic_menu_gallery));
        list.add(getButtonInfo("图文笑话", R.drawable.ic_menu_camera));
        list.add(getButtonInfo("动图笑话", R.drawable.ic_menu_manage));


        for (int i = 0; i < list.size(); i++) {
            radio_group.addView(getRadioButton(list.get(i)));
        }
    }

    private View getRadioButton(ButtonInfo buttonInfo) {

        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(buttonInfo.getName());
//        radioButton.setCompoundDrawables();
//        radioButton.setB

        return null;
    }

    public ButtonInfo getButtonInfo(String name, int iconId) {
        ButtonInfo buttonInfo = new ButtonInfo();
        buttonInfo.setName(name);
        buttonInfo.setIconId(iconId);
        return buttonInfo;
    }

}
