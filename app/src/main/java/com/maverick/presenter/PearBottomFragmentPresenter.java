package com.maverick.presenter;

import android.content.Context;
import android.util.Log;

import com.maverick.bean.PearItemInfo;
import com.maverick.bean.PearVideoDetailBean;
import com.maverick.bean.PearVideoDetailInfoData;
import com.maverick.imodel.IPearModel;
import com.maverick.model.PearModel;
import com.maverick.presenter.implView.IPearBottomFragmentView;
import com.maverick.type.LineType;
import com.maverick.type.PearItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public class PearBottomFragmentPresenter extends BasePresenter {

    private Context mContext;
    private IPearBottomFragmentView mView;
    private final IPearModel mModel;

    List<PearItemInfo> mList = new ArrayList<>();

    public PearBottomFragmentPresenter(Context context, IPearBottomFragmentView view) {
        this.mContext = context;
        this.mView = view;
        this.mModel = new PearModel();
    }

    @Override
    public void release() {
        if (mModel != null) {
            mModel.release();
        }
    }

    public void loadData(PearVideoDetailBean info) {
        if (info == null) {
            mView.onShowErrorView();
            return;
        }
        Log.e("lmf", "info.getContId() = " + info.getContId());
        mModel.requestPearDetail(info.getContId(), new IPearModel.OnPearDetailListener() {
            @Override
            public void onSuccess(PearVideoDetailInfoData info) {
                if (info.getContent() != null) {
                    mView.onShowVideoView(info.getContent().getVideos());
                }

                PearItemInfo detail = new PearItemInfo();
                detail.setType(PearItemType.DETAIL);
                detail.setLineType(LineType.SMALL);
                detail.setPearVideoDetailInfo(info.getContent());
                mList.add(detail);

                if (info.getContent() != null) {
                    PearItemInfo nodeInfo = new PearItemInfo();
                    nodeInfo.setType(PearItemType.DINGYUE);
                    nodeInfo.setPearVideoInfoNode(info.getContent().getNodeInfo());
                    nodeInfo.setLineType(LineType.SMALL_AND_MAX);
                    mList.add(nodeInfo);
                }

                PearItemInfo tabTitle = new PearItemInfo();
                tabTitle.setType(PearItemType.TITLE);
                tabTitle.setTabTitle("相关视频");
                mList.add(tabTitle);

                if (info.getRelateConts() != null && info.getRelateConts().size() > 0) {
                    for (int i = 0; i < info.getRelateConts().size(); i++) {
                        PearItemInfo infos = new PearItemInfo();
                        infos.setType(PearItemType.ITEM);
                        infos.setPearVideoInfo(info.getRelateConts().get(i));
                        infos.setRelateContPosition(i);
                        infos.setRelateContCount(info.getRelateConts().size());
                        mList.add(infos);
                    }
                }

                if (info.getContent() != null) {
                    PearItemInfo tag = new PearItemInfo();
                    tag.setType(PearItemType.TAG);
                    tag.setPearTagInfo(info.getContent().getTags());
                    mList.add(tag);
                }

                PearItemInfo commentTitle = new PearItemInfo();
                commentTitle.setType(PearItemType.TITLE);
                commentTitle.setTabTitle("热评论");
                commentTitle.setLineType(LineType.SMALL);
                mList.add(commentTitle);

                if (info != null && info.getPostInfo() != null && info.getPostInfo().getChildList() != null && info.getPostInfo().getChildList().size() > 0) {
                    for (int i = 0; i < info.getPostInfo().getChildList().size(); i++) {
                        PearItemInfo comment = new PearItemInfo();
                        comment.setType(PearItemType.COMMENT);
                        comment.setCommentInfo(info.getPostInfo().getChildList().get(i));
                        mList.add(comment);
                    }

                    PearItemInfo more = new PearItemInfo();
                    more.setType(PearItemType.COMMENT_MORE);
                    mList.add(more);
                } else {
                    PearItemInfo comment = new PearItemInfo();
                    comment.setType(PearItemType.COMMENT_EMPTY);
                    mList.add(comment);
                }

                mView.onShowSuccessView(mList);
            }

            @Override
            public void onFail() {
                mView.onShowErrorView();
            }
        });
    }

    public List<PearItemInfo> getList() {
        return mList;
    }
}
