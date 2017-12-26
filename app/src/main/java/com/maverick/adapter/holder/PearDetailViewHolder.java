package com.maverick.adapter.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.maverick.R;
import com.maverick.base.ThemedViewHolder;
import com.maverick.bean.PearVideoDetailInfo;
import com.maverick.bean.PearVideoInfoAuthor;
import com.maverick.bean.PearVideoInfoNode;
import com.maverick.util.GlideUtil;

import java.util.List;

import cntv.themelibrary.ThemeHelper;

/**
 * Created by Administrator on 2017/11/2.
 */

public class PearDetailViewHolder extends ThemedViewHolder implements View.OnClickListener {

    private final TextView title, time, name, type, detail_title, down_title, collect_title, love_title, detail_content, detail_source;
    private final View love_root, collect_root, down_root, detail_root;
    private final ExpandableRelativeLayout expandableLayout;

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


        expandableLayout = (ExpandableRelativeLayout) itemView.findViewById(R.id.expandableLayout);
        expandableLayout.expand();
        detail_content = (TextView) itemView.findViewById(R.id.detail_content);
        detail_source = (TextView) itemView.findViewById(R.id.detail_source);

        detail_root.setOnClickListener(this);

// toggle expand, collapse

// expand
//        expandableLayout.expand();
//// collapse
//        expandableLayout.collapse();
//
//// move position of child view
//        expandableLayout.moveChild(0);
//// move optional position
//        expandableLayout.move(500);
//
//// set base position which is close position
//        expandableLayout.setClosePosition(500);
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

        detail_content.setText(TextUtils.isEmpty(info.getSummary()) ? "" : info.getSummary());
        detail_source.setText(TextUtils.isEmpty(info.getSource()) ? "" : info.getSource());
    }

    @Override
    public void onClick(View v) {
        expandableLayout.toggle();
    }

    @Override
    public void refreshTheme(ThemeHelper themeHelper) {
        title.setTextColor(themeHelper.getTextColor());
        time.setTextColor(themeHelper.getSubTextColor());

        detail_title.setTextColor(themeHelper.getSubTextColor());
        down_title.setTextColor(themeHelper.getSubTextColor());
        collect_title.setTextColor(themeHelper.getSubTextColor());
        love_title.setTextColor(themeHelper.getSubTextColor());

        detail_content.setTextColor(themeHelper.getTextColor());
    }
}
