package com.maverick.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.maverick.R;

/**
 * Created by limingfei on 2018/2/9.
 */

public class ThemeSwitchCompatView extends CardView {

    private TextView mTv_title_switch_compat_theme;
    private SwitchCompat mSc_switch_compat_theme;

    public ThemeSwitchCompatView(Context context) {
        this(context, null);
    }

    public ThemeSwitchCompatView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemeSwitchCompatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.view_switch_compat_theme, this);
        mTv_title_switch_compat_theme = (TextView) view.findViewById(R.id.tv_title_switch_compat_theme);
        mSc_switch_compat_theme = (SwitchCompat) view.findViewById(R.id.sc_switch_compat_theme);
    }

    public void setTitle(CharSequence text) {
        mTv_title_switch_compat_theme.setText(text);
    }

    public void setChecked(boolean checked) {
        mSc_switch_compat_theme.setChecked(checked);
    }

    public boolean isChecked() {
        return mSc_switch_compat_theme.isChecked();
    }

    public void setOnCheckedChangeListener(SwitchCompat.OnCheckedChangeListener listener) {
        mSc_switch_compat_theme.setOnCheckedChangeListener(listener);
    }
}
