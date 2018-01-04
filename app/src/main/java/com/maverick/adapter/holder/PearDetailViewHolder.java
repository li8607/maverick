package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.maverick.R;
import com.maverick.bean.PearVideoDetailInfo;
import com.maverick.bean.PearVideoInfoAuthor;
import com.maverick.global.UMengMobclickAgent;
import com.maverick.hepler.BeanHelper;
import com.maverick.model.CollectModel;
import com.maverick.model.DingCaiModel;
import com.maverick.util.Utils;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;
import cntv.greendaolibrary.dbbean.DingCai;

/**
 * Created by Administrator on 2017/11/2.
 */

public class PearDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView title, time, name, type, detail_title, down_title, collect_title, love_title, detail_content, detail_source;
    private final View love_root, collect_root, down_root, detail_root;
    private final ExpandableRelativeLayout expandableLayout;
    private PearVideoDetailInfo mInfo;
    private Context mContext;
    private final View detail;

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

        detail = itemView.findViewById(R.id.detail);

        expandableLayout = (ExpandableRelativeLayout) itemView.findViewById(R.id.expandableLayout);
        expandableLayout.expand();
        detail_content = (TextView) itemView.findViewById(R.id.detail_content);
        detail_source = (TextView) itemView.findViewById(R.id.detail_source);

        detail_root.setOnClickListener(this);
        love_root.setOnClickListener(this);
        collect_root.setOnClickListener(this);
        down_root.setOnClickListener(this);

        expandableLayout.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }

            @Override
            public void onPreOpen() {
                detail.animate().rotation(-180).setDuration(500).start();
            }

            @Override
            public void onPreClose() {
                detail.animate().rotation(0).setDuration(500).start();
            }

            @Override
            public void onOpened() {

            }

            @Override
            public void onClosed() {

            }
        });

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
        this.mInfo = info;
        this.mContext = context;

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
        updateUI(info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.love_root:
                if (mInfo == null) {
                    return;
                }

                if (mInfo.isDing()) {
                    DingCai dingCai = new DingCai();
                    dingCai.setDingCaiId(mInfo.getContId());
                    DingCaiModel.newInstance().deleteSisterDingCaiDB(dingCai);
                } else {
                    DingCai dingCai = new DingCai();
                    dingCai.setDingCaiId(mInfo.getContId());
                    dingCai.setDing(true);
                    DingCaiModel.newInstance().insertSisterDingCaiDB(dingCai);
                }

                mInfo.setDing(!mInfo.isDing());
                updateUI(mInfo);
                break;
            case R.id.collect_root:

                if (mInfo == null) {
                    return;
                }

                Collect collect = BeanHelper.getCollect(mInfo);
                mInfo.setCollect(!mInfo.isCollect());
                MobclickAgent.onEvent(mContext, UMengMobclickAgent.Collect);
                if (mInfo.isCollect()) {
                    CollectModel.newInstance().insertCollectDB(collect);
                } else {
                    CollectModel.newInstance().deleteCollectDB(collect);
                }

                updateUI(mInfo);
                break;
            case R.id.down_root:
                break;
            case R.id.detail_root:
                expandableLayout.toggle();
                break;
        }
    }

    private void updateUI(PearVideoDetailInfo info) {
        if (info == null) {
            return;
        }

        love_root.setSelected(info.isDing());
        collect_root.setSelected(info.isCollect());
        down_root.setSelected(info.isDownload());
        collect_title.setText(info.isCollect() ? mContext.getString(R.string.collect_select) : mContext.getString(R.string.collect));
        love_title.setText(info.isDing() ? (Utils.getString2Int(info.getPraiseTimes()) + 1 + "") : info.getPraiseTimes());
    }
}
