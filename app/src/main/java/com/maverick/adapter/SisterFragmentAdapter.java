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
import com.maverick.adapter.holder.SisterTextHolder;
import com.maverick.adapter.holder.SisterVideoHolder;
import com.maverick.bean.SisterInfo;
import com.maverick.global.Tag;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterFragmentAdapter extends RecyclerView.Adapter {

    private String TAG = getClass().getSimpleName();

    public static final int IMAGE = 1;
    public static final int TEXT = 2;
    public static final int VIDEO = 3;

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
            case TEXT:
                holder = new SisterTextHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_sister_text, parent, false));
                break;
            case VIDEO:
                holder = new SisterVideoHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_sister_video, parent, false));
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

        if(holder instanceof SisterVideoHolder) {
            SisterVideoHolder sisterVideoHolder = (SisterVideoHolder) holder;
            sisterVideoHolder.setListVideoUtil(listVideoUtil);
            sisterVideoHolder.setRecyclerAdapter(this);
            sisterVideoHolder.bindData(mContext, mList.get(position));
        }else if (holder instanceof SisterTextHolder) {
            SisterTextHolder sisterTextHolder = (SisterTextHolder) holder;
            sisterTextHolder.bindData(mContext, mList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        SisterInfo sisterInfo = mList.get(position);
        String type = sisterInfo.getType();
        Log.e(TAG, type);
        if (TextUtils.equals(type, Tag.SISTER_IMAGE)) {
            //图片
            return IMAGE;
        } else if (TextUtils.equals(type, Tag.SISTER_TEXT)) {
            //段子
            return TEXT;
        } else if (TextUtils.equals(type, Tag.SISTER_AUDIO)) {
            //声音
            return VIDEO;
        } else if (TextUtils.equals(type, Tag.SISTER_VIDEO)) {
            //视频
            return VIDEO;
        }
        return TEXT;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<SisterInfo> list) {
        this.mList = list;
    }

    public void setMoreData(List<SisterInfo> list) {
        if (mList != null) {
            mList.addAll(list);
        }
    }

    public List<SisterInfo> getData() {
        return mList;
    }

    private ListVideoUtil listVideoUtil;

    public void setListVideoUtil(ListVideoUtil listVideoUtil) {
        this.listVideoUtil = listVideoUtil;
    }
}
