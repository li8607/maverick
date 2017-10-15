package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maverick.R;
import com.maverick.bean.SisterInfo;
import com.maverick.model.CollectModel;
import com.maverick.model.SisterDingCaiModel;
import com.maverick.type.CollectType;
import com.maverick.util.GlideUtil;
import com.maverick.util.Utils;

import cntv.greendaolibrary.dbbean.Collect;
import cntv.greendaolibrary.dbbean.SisterDingCai;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterTextHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected String TAG = getClass().getSimpleName();

    protected SisterInfo mSisterInfo;
    protected final ImageView image_head;
    protected final TextView time, name;
    protected final TextView content;
    protected final TextView text_ding_count, text_cai_count, text_share_count, text_comment_count;
    private final View root_ding;
    private final View root_cai;
    private final View root_share;
    private final View root_comment;

    public SisterTextHolder(View itemView) {
        super(itemView);

        image_head = (ImageView) itemView.findViewById(R.id.image_head);
        time = (TextView) itemView.findViewById(R.id.time);
        content = (TextView) itemView.findViewById(R.id.content);

        text_ding_count = (TextView) itemView.findViewById(R.id.text_ding_count);
        text_cai_count = (TextView) itemView.findViewById(R.id.text_cai_count);
        text_share_count = (TextView) itemView.findViewById(R.id.text_share_count);
        text_comment_count = (TextView) itemView.findViewById(R.id.text_comment_count);

        root_ding = itemView.findViewById(R.id.root_ding);
        root_cai = itemView.findViewById(R.id.root_cai);
        root_share = itemView.findViewById(R.id.root_share);
        root_comment = itemView.findViewById(R.id.root_comment);

        root_ding.setOnClickListener(this);
        root_cai.setOnClickListener(this);
        root_share.setOnClickListener(this);
        root_comment.setOnClickListener(this);

        name = (TextView) itemView.findViewById(R.id.name);
    }

    public void bindData(Context context, SisterInfo sisterInfo) {
        this.mSisterInfo = sisterInfo;
        GlideUtil.loadCircleImage(context, mSisterInfo.getProfile_image(), image_head);

        if (!TextUtils.isEmpty(sisterInfo.getName())) {
            name.setText(sisterInfo.getName().trim());
        } else {
            name.setText("");
        }

        if (!TextUtils.isEmpty(sisterInfo.getCreate_time())) {
            time.setText(sisterInfo.getCreate_time().trim());
        } else {
            time.setText("");
        }

        if (!TextUtils.isEmpty(sisterInfo.getText())) {
            Log.e(TAG, "sisterInfo.getText() = " + sisterInfo.getText());
            content.setText(sisterInfo.getText().trim());
        } else {
            content.setText("");
        }

        updateDingCaiUI(mSisterInfo);


        if (!TextUtils.isEmpty(sisterInfo.getShare())) {
            text_share_count.setText(sisterInfo.getShare().trim());
        } else {
            text_share_count.setText(text_share_count.getResources().getString(R.string.share));
        }

//        if (!TextUtils.isEmpty(sisterInfo.getComment())) {
//            text_comment_count.setText(sisterInfo.getComment().trim());
//        } else {
//            text_comment_count.setText(text_comment_count.getResources().getString(R.string.comment));
//        }

        if (mSisterInfo.isCollect()) {
            text_comment_count.setText(text_comment_count.getContext().getString(R.string.collect_select));
            root_comment.setSelected(true);
        } else {
            text_comment_count.setText(text_comment_count.getContext().getString(R.string.collect));
            root_comment.setSelected(false);
        }
    }

    public void updateDingCaiUI(SisterInfo sisterInfo) {

        if (sisterInfo == null) {
            return;
        }

        int love = Utils.getString2Int(sisterInfo.getLove().trim());
        if (sisterInfo.isDing()) {
            love++;
        }
        text_ding_count.setText(love + "");
        root_ding.setSelected(sisterInfo.isDing());

        int hate = Utils.getString2Int(sisterInfo.getHate().trim());
        if (sisterInfo.isCai()) {
            hate++;
        }
        text_cai_count.setText(hate + "");
        root_cai.setSelected(sisterInfo.isCai());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.root_ding:
                if (mSisterInfo == null) {
                    return;
                }

                if (mSisterInfo.isDing()) {
                    SisterDingCai sisterDingCai = new SisterDingCai();
                    sisterDingCai.setDingCaiId(mSisterInfo.getId());
                    SisterDingCaiModel.newInstance().deleteSisterDingCaiDB(sisterDingCai);
                    mSisterInfo.setDing(false);
                    mSisterInfo.setCai(false);
                } else {
                    SisterDingCai sisterDingCai = new SisterDingCai();
                    sisterDingCai.setDingCaiId(mSisterInfo.getId());
                    sisterDingCai.setDing(true);
                    sisterDingCai.setCai(false);
                    SisterDingCaiModel.newInstance().insertSisterDingCaiDB(sisterDingCai);

                    mSisterInfo.setDing(true);
                    mSisterInfo.setCai(false);
                }

                updateDingCaiUI(mSisterInfo);
                break;
            case R.id.root_cai:
                if (mSisterInfo == null) {
                    return;
                }
                if (mSisterInfo.isCai()) {
                    SisterDingCai sisterDingCai = new SisterDingCai();
                    sisterDingCai.setDingCaiId(mSisterInfo.getId());
                    SisterDingCaiModel.newInstance().deleteSisterDingCaiDB(sisterDingCai);
                    mSisterInfo.setDing(false);
                    mSisterInfo.setCai(false);
                }else {
                    SisterDingCai sisterDingCai = new SisterDingCai();
                    sisterDingCai.setDingCaiId(mSisterInfo.getId());
                    sisterDingCai.setDing(false);
                    sisterDingCai.setCai(true);
                    SisterDingCaiModel.newInstance().insertSisterDingCaiDB(sisterDingCai);

                    mSisterInfo.setDing(false);
                    mSisterInfo.setCai(true);
                }
                updateDingCaiUI(mSisterInfo);
                break;
            case R.id.root_share:
                Toast.makeText(v.getContext(), "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.root_comment:
                if (mSisterInfo == null) {
                    return;
                }

                Collect collect = new Collect();
                collect.setCollectType(CollectType.SISTER);
                collect.setCollectMajorKey(mSisterInfo.getId());
                collect.setCollectTime(System.currentTimeMillis());
                collect.setCollectCT(mSisterInfo.getCreate_time());
                collect.setCollectName(mSisterInfo.getText());
                collect.setCollectImage(mSisterInfo.getImage2());
                collect.setCollectItemType(mSisterInfo.getType());

                mSisterInfo.setCollect(!mSisterInfo.isCollect());

                if (mSisterInfo.isCollect()) {
                    CollectModel.newInstance().insertCollectDB(collect);
                    text_comment_count.setText(v.getContext().getString(R.string.collect_select));
                    root_comment.setSelected(true);
                } else {
                    CollectModel.newInstance().deleteCollectDB(collect);
                    text_comment_count.setText(v.getContext().getString(R.string.collect));
                    root_comment.setSelected(false);
                }
                break;
        }
    }
}
