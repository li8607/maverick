package com.maverick.dialog;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.MainApp;
import com.maverick.R;
import com.maverick.adapter.holder.ThemeHolder;
import com.maverick.base.BaseDialogFragment;
import com.maverick.bean.ThemeInfo;
import com.maverick.global.SPKey;
import com.maverick.global.ThemeType;
import com.maverick.presenter.BasePresenter;
import com.maverick.util.DensityUtil;
import com.maverick.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limingfei on 2018/1/19.
 */

public class ThemeDialog extends BaseDialogFragment {

    private RecyclerView rv_theme;
    private List<ThemeInfo> mList;
    private int mThemeType;
    private ThemeAdapter mThemeAdapter;

    public static ThemeDialog newInstance() {
        ThemeDialog dialog = new ThemeDialog();
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ThemeOverlay_AppCompat_Dialog_Alert);
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.dialog_theme;
    }

    @Override
    protected void onInitView(View view) {
        rv_theme = findView(R.id.rv_theme);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_theme.setLayoutManager(layoutManager);

        rv_theme.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int bottom = DensityUtil.dip2px(getContext(), 5);
                int rl = DensityUtil.dip2px(getContext(), 10);
                outRect.bottom = bottom;
                outRect.top = bottom;
                outRect.left = rl;
                outRect.right = rl;
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mThemeType = PreferenceUtil.getInstance(MainApp.mContext).getInt(SPKey.THEME, 0);
        mList = new ArrayList<>();
        mList.add(getThemeInfo(R.color.colorPrimaryPink, getString(R.string.theme_pink_title), ThemeType.PINK));
        mList.add(getThemeInfo(R.color.colorPrimaryRed, getString(R.string.theme_red_title), ThemeType.RED));
        mList.add(getThemeInfo(R.color.colorPrimaryYellow, getString(R.string.theme_yellow_title), ThemeType.YELLOW));
        mList.add(getThemeInfo(R.color.colorPrimaryGreen, getString(R.string.theme_green_title), ThemeType.GREEN));
        mList.add(getThemeInfo(R.color.colorPrimaryBlue, getString(R.string.theme_blue_title), ThemeType.BLUE));
        mList.add(getThemeInfo(R.color.colorPrimaryPurple, getString(R.string.theme_purple_title), ThemeType.PURPLE));
        mThemeAdapter = new ThemeAdapter(mList);
        rv_theme.setAdapter(mThemeAdapter);
    }

    public ThemeInfo getThemeInfo(int colorId, String title, int themeType) {
        ThemeInfo themeInfo = new ThemeInfo();
        themeInfo.setColorId(colorId);
        themeInfo.setTitle(title);
        themeInfo.setThemeType(themeType);
        return themeInfo;
    }


    private void updateTheme(int position) {
        ThemeInfo themeInfo = mList.get(position);
        mThemeType = mList.get(position).getThemeType();
        PreferenceUtil.getInstance(MainApp.mContext).putInt(SPKey.THEME, themeInfo.getThemeType());
        mThemeAdapter.notifyDataSetChanged();
    }

    public class ThemeAdapter extends RecyclerView.Adapter {

        private List<ThemeInfo> mList;
        private LayoutInflater mLayoutInflater;

        public ThemeAdapter(List<ThemeInfo> list) {
            this.mList = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mLayoutInflater == null) {
                mLayoutInflater = LayoutInflater.from(parent.getContext());
            }
            return new ThemeHolder(mLayoutInflater.inflate(R.layout.item_theme, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ThemeHolder themeHolder = (ThemeHolder) holder;
            themeHolder.tv_color_title.setText(mList.get(position).getTitle());
            themeHolder.v_color.setBackgroundResource(mList.get(position).getColorId());

            themeHolder.tv_sure.setVisibility(mThemeType == mList.get(position).getThemeType() ? View.VISIBLE : View.INVISIBLE);
            themeHolder.tv_sure.setText(mThemeType == mList.get(position).getThemeType() ? "使用中" : "使用");
            themeHolder.tv_sure.setSelected(mThemeType == mList.get(position).getThemeType());
            themeHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mThemeType != mList.get(position).getThemeType()) {
                        updateTheme(position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }
    }
}
