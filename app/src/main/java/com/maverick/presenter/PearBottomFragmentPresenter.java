package com.maverick.presenter;

import android.content.Context;

import com.maverick.bean.PearItemInfo;
import com.maverick.bean.PearVideoDetailBean;
import com.maverick.bean.PearVideoDetailInfoData;
import com.maverick.imodel.IPearModel;
import com.maverick.model.PearModel;
import com.maverick.presenter.implView.IPearBottomFragmentView;
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
        mModel.requestPearDetail(info.getContId(), new IPearModel.OnPearDetailListener() {
            @Override
            public void onSuccess(PearVideoDetailInfoData info) {
                if (info.getContent() != null) {
                    mView.onShowVideoView(info.getContent().getVideos());
                }

                List<PearItemInfo> list = new ArrayList<>();

                PearItemInfo detail = new PearItemInfo();
                detail.setType(PearItemType.DETAIL);
                detail.setPearVideoDetailInfo(info.getContent());
                list.add(detail);

                PearItemInfo tabTitle = new PearItemInfo();
                tabTitle.setType(PearItemType.TITLE);
                tabTitle.setTabTitle("相关视频");
                list.add(tabTitle);

                if (info.getRelateConts() != null && info.getRelateConts().size() > 0) {
                    for (int i = 0; i < info.getRelateConts().size(); i++) {
                        PearItemInfo infos = new PearItemInfo();
                        infos.setType(PearItemType.ITEM);
                        infos.setPearVideoInfo(info.getRelateConts().get(i));
                        list.add(infos);
                    }
                }

                mView.onShowSuccessView(list);
            }

            @Override
            public void onFail() {
                mView.onShowErrorView();
            }
        });
    }
}
