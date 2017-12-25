package com.maverick.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.maverick.R;
import com.mikepenz.iconics.view.IconicsImageView;

/**
 * Created by limingfei on 2017/12/25.
 */

public class SettingBasic extends FrameLayout {

    IconicsImageView icon;
    TextView title;
    TextView caption;
    private String iconString;
    @StringRes
    private int titleRes;
    @StringRes
    private int captionRes;

    public SettingBasic(@NonNull Context context) {
        this(context, null);
    }

    public SettingBasic(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingBasic(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setBackgroundResource(R.drawable.ripple);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_setting_basic, this);

        icon = (IconicsImageView) findViewById(R.id.icon);
        title = (TextView) findViewById(R.id.title);
        caption = (TextView) findViewById(R.id.caption);

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SettingBasic);

        iconString = array.getString(R.styleable.SettingBasic_settingIcon);
        titleRes = array.getResourceId(R.styleable.SettingBasic_settingTitle, 0);
        captionRes = array.getResourceId(R.styleable.SettingBasic_settingCaption, 0);
        array.recycle();
    }

    @Override
    protected void onFinishInflate() {
        icon.setIcon(iconString);
        title.setText(titleRes);
        caption.setText(captionRes);
        super.onFinishInflate();
    }
}
