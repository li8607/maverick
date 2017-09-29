package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.holder.SisterImageHolder;
import com.maverick.bean.SisterInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterFragmentAdapter extends RecyclerView.Adapter {

    private String TAG = getClass().getSimpleName();

    public static final int IMAGE = 1;

    private List<SisterInfo> mList;
    private Context mContext;

    public SisterFragmentAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;

        switch (viewType) {
            case IMAGE:
                holder = new SisterImageHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_sister_image, parent, false));
                break;
            default:
                holder = new RecyclerView.ViewHolder(new View(parent.getContext())) {
                };
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SisterImageHolder) {
            SisterImageHolder sisterImageHolder = (SisterImageHolder) holder;
            sisterImageHolder.bindData(mContext, mList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        SisterInfo sisterInfo = mList.get(position);
        String type = sisterInfo.getType();
        Log.e(TAG, type);
        if (TextUtils.equals(type, "10")) {
            //图片
            return IMAGE;
        } else if (TextUtils.equals(type, "29")) {
            //段子

        } else if (TextUtils.equals(type, "31")) {
            //声音

        } else if (TextUtils.equals(type, "41 ")) {
            //视频

        }
        return IMAGE;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<SisterInfo> list) {
        this.mList = list;
    }
}
