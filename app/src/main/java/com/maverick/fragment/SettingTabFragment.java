package com.maverick.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.maverick.R;
import com.maverick.adapter.SettingTabFragmentAdapter;
import com.maverick.base.BaseFragment;
import com.maverick.bean.SettingItemInfo;
import com.maverick.bean.SettingTabInfo;
import com.maverick.dialog.ChoiceDialog;
import com.maverick.dialog.ThemeDialog;
import com.maverick.global.SPKey;
import com.maverick.presenter.BasePresenter;
import com.maverick.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limingfei on 2018/3/9.
 */

public class SettingTabFragment extends BaseFragment implements SettingTabFragmentAdapter.OnItemClickListener, ThemeDialog.OnThemeChangeListener {

    private RecyclerView mRecyclerView;
    private List<Object> mList;
    private SettingTabFragmentAdapter mAdapter;
    private int mNightMode;
    private int mTheme;
    private String[] mVoicer_cloud_entries;
    private String[] mVoicer_cloud_values;

    public static SettingTabFragment newInstance() {
        SettingTabFragment fragment = new SettingTabFragment();
        return fragment;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_setting_tab;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void onInitView(View view) {
//        final int space = DensityUtil.dip2px(getContext(), 1);
        final int space = 1;
        final Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorDivider2));

        mRecyclerView = view.findViewById(R.id.rv_setting_tab);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = space;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                final int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    int position = parent.getChildAdapterPosition(child);
                    if (position == parent.getAdapter().getItemCount() - 1
                            || parent.getAdapter().getItemViewType(position) == SettingTabFragmentAdapter.TAB_TITLE
                            || parent.getAdapter().getItemViewType(position + 1) == SettingTabFragmentAdapter.TAB_TITLE) {
                        continue;
                    }

                    c.drawLine(parent.getLeft(), child.getBottom() + space, parent.getRight(), child.getBottom() + space, mPaint);
                }
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        String[] setting_entries = getResources().getStringArray(R.array.setting_entries);
        String[] setting_values = getResources().getStringArray(R.array.setting_values);

        String[] theme_entries = getResources().getStringArray(R.array.theme_entries);
        String[] theme_values = getResources().getStringArray(R.array.theme_values);

        String[] play_entries = getResources().getStringArray(R.array.play_entries);
        String[] play_values = getResources().getStringArray(R.array.play_values);



        mList = new ArrayList<>();

        for (int i = 0; i < setting_values.length; i++) {
            SettingTabInfo settingTabInfo = new SettingTabInfo();
            settingTabInfo.setTitle(setting_entries[i]);
            settingTabInfo.setType(setting_values[i]);
            mList.add(settingTabInfo);
            if (TextUtils.equals(setting_values[i], "0")) {
                //基本样式
                for (int j = 0; j < theme_values.length; j++) {
                    SettingItemInfo settingItemInfo = new SettingItemInfo();
                    settingItemInfo.setTitle(theme_entries[j]);
                    settingItemInfo.setType(theme_values[j]);
                    settingItemInfo.setGroupType(settingTabInfo.getType());
                    mList.add(settingItemInfo);
                }
            } else if (TextUtils.equals(setting_values[i], "1")) {
                //播放
                for (int j = 0; j < play_values.length; j++) {
                    SettingItemInfo settingItemInfo = new SettingItemInfo();
                    settingItemInfo.setTitle(play_entries[j]);
                    settingItemInfo.setType(play_values[j]);
                    settingItemInfo.setGroupType(settingTabInfo.getType());
                    mList.add(settingItemInfo);
                }
            }
        }

        mAdapter = new SettingTabFragmentAdapter(getContext(), mList);
        mNightMode = PreferenceUtil.getInstance(getContext()).getInt(SPKey.NIGHT, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        mTheme = PreferenceUtil.getInstance(getContext()).getInt(SPKey.THEME, 0);
        mAdapter.setNightMode(mNightMode);
        mAdapter.setTheme(mTheme);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case SettingTabFragmentAdapter.ITEM:
                SettingItemInfo settingItemInfo = (SettingItemInfo) mList.get(position);
                SettingTabInfo settingTabInfo = null;
                for (int i = position - 1; i > -1; i--) {
                    Object object = mList.get(i);
                    if (object instanceof SettingTabInfo) {
                        settingTabInfo = (SettingTabInfo) object;
                        break;
                    }
                }

                if (settingTabInfo == null) {
                    return;
                }

                if (TextUtils.equals(settingTabInfo.getType(), "0")) {
                    //基本样式
                    if (TextUtils.equals(settingItemInfo.getType(), "0")) {
                        //夜间模式
                        switch (mNightMode) {
                            case AppCompatDelegate.MODE_NIGHT_YES:
                                mNightMode = AppCompatDelegate.MODE_NIGHT_NO;
                                break;
                            default:
                                mNightMode = AppCompatDelegate.MODE_NIGHT_YES;
                                break;
                        }

                        mAdapter.setNightMode(mNightMode);
                        mAdapter.notifyDataSetChanged();

                        if (mOnDayNightChangeListener != null) {
                            mOnDayNightChangeListener.onDayNightChange(mNightMode);
                        }

                    } else if (TextUtils.equals(settingItemInfo.getType(), "1")) {
                        //自动切换日夜模式
                        switch (mNightMode) {
                            case AppCompatDelegate.MODE_NIGHT_AUTO:
                                mNightMode = AppCompatDelegate.MODE_NIGHT_NO;
                                break;
                            default:
                                mNightMode = AppCompatDelegate.MODE_NIGHT_AUTO;
                                break;
                        }

                        mAdapter.setNightMode(mNightMode);
                        mAdapter.notifyDataSetChanged();

                        if (mOnDayNightChangeListener != null) {
                            mOnDayNightChangeListener.onDayNightChange(mNightMode);
                        }

                    } else if (TextUtils.equals(settingItemInfo.getType(), "2")) {
                        //主题
                        ThemeDialog dialog = ThemeDialog.newInstance();
                        dialog.setOnThemeChangeListener(this);
                        dialog.show(getChildFragmentManager(), "");
                    }
                } else if (TextUtils.equals(settingTabInfo.getType(), "1")) {
                    //播放
                    if (TextUtils.equals(settingItemInfo.getType(), "0")) {

                        if(mVoicer_cloud_entries == null) {
                            mVoicer_cloud_entries = getResources().getStringArray(R.array.voicer_cloud_entries);
                        }

                        if(mVoicer_cloud_values == null) {
                            mVoicer_cloud_values = getResources().getStringArray(R.array.voicer_cloud_values);
                        }

                        //发音人
                        ChoiceDialog choiceDialog = ChoiceDialog.newInstance(ChoiceDialog.SINGLE, settingItemInfo.getTitle(), mVoicer_cloud_entries, "", "取消");
                        choiceDialog.show(getChildFragmentManager(), "");
                    }
                }

                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDayNightChangeListener) {
            mOnDayNightChangeListener = (OnDayNightChangeListener) context;
        }
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        if(childFragment instanceof ThemeDialog) {
            ThemeDialog themeDialog = (ThemeDialog) childFragment;
            themeDialog.setOnThemeChangeListener(this);
        }
        super.onAttachFragment(childFragment);
    }

    private OnDayNightChangeListener mOnDayNightChangeListener;

    @Override
    public void onThemeChange(int themeType) {
        if (mOnDayNightChangeListener != null) {
            mOnDayNightChangeListener.onThemeChange(themeType);
        }
    }

    public interface OnDayNightChangeListener {
        void onDayNightChange(int nightMode);

        void onThemeChange(int themeType);
    }
}
