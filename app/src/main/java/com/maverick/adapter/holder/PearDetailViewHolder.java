package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.bean.PearVideoDetailInfo;
import com.maverick.bean.PearVideoInfoAuthor;
import com.maverick.bean.PearVideoInfoNode;
import com.maverick.util.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/2.
 */

public class PearDetailViewHolder extends RecyclerView.ViewHolder {

    private final TextView title, time, name, type, detail_title, down_title, collect_title, love_title, dingyue_btn, dingyue_title, dingyue_huati;
    private final View love_root, collect_root, down_root, detail_root;
    private final ImageView dingyue_image;

    public PearDetailViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        time = (TextView) itemView.findViewById(R.id.time);
        name = (TextView) itemView.findViewById(R.id.name);
        type = (TextView) itemView.findViewById(R.id.type);

        love_root = itemView.findViewById(R.id.love_root);
        collect_root = itemView.findViewById(R.id.collect_root);
        down_root = itemView.findViewById(R.id.down_root);
        detail_root = itemView.findViewById(R.id.detail_root);

        detail_title = (TextView) itemView.findViewById(R.id.detail_title);
        down_title = (TextView) itemView.findViewById(R.id.down_title);
        collect_title = (TextView) itemView.findViewById(R.id.collect_title);
        love_title = (TextView) itemView.findViewById(R.id.love_title);

        dingyue_btn = (TextView) itemView.findViewById(R.id.dingyue_btn);
        dingyue_title = (TextView) itemView.findViewById(R.id.dingyue_title);
        dingyue_huati = (TextView) itemView.findViewById(R.id.dingyue_huati);
        dingyue_image = (ImageView) itemView.findViewById(R.id.dingyue_image);
    }

    public void bindData(Context context, PearVideoDetailInfo info) {

        title.setText(TextUtils.isEmpty(info.getName()) ? "" : info.getName());


        List<PearVideoInfoAuthor> list = info.getAuthors();

        if (list != null && list.size() > 0) {
            PearVideoInfoAuthor author = list.get(0);
            time.setText(TextUtils.isEmpty(info.getPubTime()) ? "" : info.getPubTime() + (TextUtils.isEmpty(author.getNickname()) ? "" : " | "));
            name.setText(TextUtils.isEmpty(author.getNickname()) ? "" : author.getNickname());
            type.setText(TextUtils.isEmpty(info.getCornerLabelDesc()) ? "" : info.getCornerLabelDesc());
            type.setVisibility(View.VISIBLE);
        } else {
            time.setText(TextUtils.isEmpty(info.getPubTime()) ? "" : info.getPubTime());
            name.setText("");
            type.setText("");
            type.setVisibility(View.INVISIBLE);
        }


        PearVideoInfoNode nodeInfo = info.getNodeInfo();
        if (nodeInfo != null) {
            GlideUtil.loadCircleImage(context, nodeInfo.getLogoImg(), dingyue_image);
            dingyue_title.setText(TextUtils.isEmpty(nodeInfo.getName()) ? "" : nodeInfo.getName());
            dingyue_huati.setText(TextUtils.isEmpty(nodeInfo.getDesc()) ? "" : nodeInfo.getDesc());
        } else {
            dingyue_title.setText("");
            dingyue_huati.setText("");
        }
    }
}
