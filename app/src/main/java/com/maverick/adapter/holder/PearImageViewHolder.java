package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.bean.PearVideoInfo;
import com.maverick.util.GlideUtil;
import com.maverick.weight.RatioImageView;

/**
 * Created by Administrator on 2017/10/27.
 */

public class PearImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final RatioImageView image;
    public final TextView title, node, label;
    private PearVideoInfo mInfo;

    public PearImageViewHolder(View itemView) {
        super(itemView);
        image = (RatioImageView) itemView.findViewById(R.id.image);
        image.setOriginalSize(16, 9);

        title = (TextView) itemView.findViewById(R.id.title);
        node = (TextView) itemView.findViewById(R.id.node);
        label = (TextView) itemView.findViewById(R.id.label);

        itemView.setOnClickListener(this);
    }

    public void bindData(Context context, PearVideoInfo info) {
        this.mInfo = info;
        String pic = info.getPic();
        GlideUtil.loadImage(context, pic, image);

        if (!TextUtils.isEmpty(info.getName())) {
            title.setText(info.getName());
        } else {
            title.setText("");
        }

        if (info.getNodeInfo() != null && !TextUtils.isEmpty(info.getNodeInfo().getName())) {
            node.setText(info.getNodeInfo().getName() + " | " + info.getDuration());
        } else {
            node.setText("");
        }

        if (!TextUtils.isEmpty(info.getCornerLabelDesc())) {
            label.setText(info.getCornerLabelDesc());
            label.setVisibility(View.VISIBLE);
        } else {
            label.setText("");
            label.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnListener != null && getAdapterPosition() >= 0) {
            mOnListener.onItemClick(getAdapterPosition(), mInfo);
        }
    }

    private OnListener mOnListener;

    public void setOnListener(OnListener listener) {
        this.mOnListener = listener;
    }

    public interface OnListener {
        void onItemClick(int position, PearVideoInfo info);
    }
}
