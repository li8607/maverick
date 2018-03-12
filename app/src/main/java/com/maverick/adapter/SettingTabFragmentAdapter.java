package com.maverick.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.holder.SettingItemViewHolder;
import com.maverick.adapter.holder.SettingTabViewHolder;
import com.maverick.bean.SettingItemInfo;
import com.maverick.bean.SettingTabInfo;

import java.util.List;

/**
 * Created by limingfei on 2018/3/9.
 */

public class SettingTabFragmentAdapter extends RecyclerView.Adapter {

    public static final int TAB_TITLE = 0;
    public static final int ITEM = 1;

    private Context mContext;
    private List<Object> mList;
    private final LayoutInflater mLayoutInflater;
    private int mNightMode;
    private int mTheme;

    public SettingTabFragmentAdapter(Context context, List<Object> list) {
        this.mContext = context;
        this.mList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case TAB_TITLE:
                holder = new SettingTabViewHolder(mLayoutInflater.inflate(R.layout.fragment_setting_tab_title, parent, false));
                break;
            default:
                holder = new SettingItemViewHolder(mLayoutInflater.inflate(R.layout.fragment_setting_tab_item, parent, false));
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case TAB_TITLE:
                SettingTabViewHolder settingTabViewHolder = (SettingTabViewHolder) holder;
                SettingTabInfo settingTabInfo = (SettingTabInfo) mList.get(position);
                settingTabViewHolder.setTitle(settingTabInfo.getTitle());
                break;
            case ITEM:
                SettingItemViewHolder settingItemViewHolder = (SettingItemViewHolder) holder;
                SettingItemInfo settingItemInfo = (SettingItemInfo) mList.get(position);
                settingItemViewHolder.setTitle(settingItemInfo.getTitle());
                if (mOnItemClickListener != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickListener.onItemClick(holder, position);
                        }
                    });
                }


                if (TextUtils.equals(settingItemInfo.getGroupType(), "0")) {
                    if (TextUtils.equals(settingItemInfo.getType(), "0")) {
                        //夜间模式
                        settingItemViewHolder.mSwitch.setVisibility(View.VISIBLE);
                        settingItemViewHolder.mTheme.setVisibility(View.GONE);
                        settingItemViewHolder.mSubTitle.setVisibility(View.GONE);
                        settingItemViewHolder.mSwitch.setChecked(mNightMode == AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    } else if (TextUtils.equals(settingItemInfo.getType(), "1")) {
                        //自动切换日夜模式
                        settingItemViewHolder.mSwitch.setVisibility(View.VISIBLE);
                        settingItemViewHolder.mTheme.setVisibility(View.GONE);
                        settingItemViewHolder.mSubTitle.setVisibility(View.GONE);
                        settingItemViewHolder.mSwitch.setChecked(mNightMode == AppCompatDelegate.MODE_NIGHT_AUTO);
                        break;
                    } else if (TextUtils.equals(settingItemInfo.getType(), "2")) {
                        settingItemViewHolder.mTheme.setVisibility(View.VISIBLE);
                        settingItemViewHolder.mSwitch.setVisibility(View.GONE);
                        settingItemViewHolder.mSubTitle.setVisibility(View.GONE);
                        break;
                    }
                } else if (TextUtils.equals(settingItemInfo.getGroupType(), "1")) {
                    if (!TextUtils.isEmpty(settingItemInfo.getBrief())) {
                        settingItemViewHolder.mSubTitle.setText(settingItemInfo.getBrief());
                        settingItemViewHolder.mSubTitle.setVisibility(View.VISIBLE);
                        settingItemViewHolder.mTheme.setVisibility(View.GONE);
                        settingItemViewHolder.mSwitch.setVisibility(View.GONE);
                        break;
                    } else if (TextUtils.equals(settingItemInfo.getType(), "5")) {
                        settingItemViewHolder.mSwitch.setVisibility(View.VISIBLE);
                        settingItemViewHolder.mTheme.setVisibility(View.GONE);
                        settingItemViewHolder.mSubTitle.setVisibility(View.GONE);
                        settingItemViewHolder.mSwitch.setChecked(settingItemInfo.isKey_request_focus());
                        break;
                    }
                }
                settingItemViewHolder.mTheme.setVisibility(View.GONE);
                settingItemViewHolder.mSwitch.setVisibility(View.GONE);
                settingItemViewHolder.mSubTitle.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position) instanceof SettingTabInfo) {
            return TAB_TITLE;
        } else {
            return ITEM;
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setNightMode(int night) {
        this.mNightMode = night;
    }

    public void setTheme(int theme) {
        this.mTheme = theme;
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView.ViewHolder viewHolder, int position);
    }
}
