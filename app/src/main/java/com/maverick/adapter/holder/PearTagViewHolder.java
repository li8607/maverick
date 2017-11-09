package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.maverick.R;
import com.maverick.bean.PearVideoDetailInfoTag;

import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created by limingfei on 2017/11/9.
 */
public class PearTagViewHolder extends RecyclerView.ViewHolder implements TagView.OnTagClickListener {

    private final TagContainerLayout tagContainerLayout;
    private Context mContext;
    List<PearVideoDetailInfoTag> mList;

    public PearTagViewHolder(View itemView) {
        super(itemView);

        tagContainerLayout = (TagContainerLayout) itemView.findViewById(R.id.tagContainerLayout);
        tagContainerLayout.setOnTagClickListener(this);
    }

    public void bindData(Context context, List<PearVideoDetailInfoTag> list) {
        this.mContext = context;
        this.mList = list;
        tagContainerLayout.removeAllTags();
        for (int i = 0; i < list.size(); i++) {
            tagContainerLayout.addTag(list.get(i).getName());
        }
    }

    @Override
    public void onTagClick(int position, String text) {
//        http://app.pearvideo.com/clt/jsp/v2/content.jsp?tagId=1365
        Toast.makeText(mContext, mList.get(position).getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTagLongClick(int position, String text) {

    }

    @Override
    public void onTagCrossClick(int position) {

    }
}
