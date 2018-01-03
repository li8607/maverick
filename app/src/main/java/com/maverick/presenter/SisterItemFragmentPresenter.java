package com.maverick.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.maverick.bean.SisterInfo;
import com.maverick.bean.SisterTabInfo;
import com.maverick.hepler.BeanHelper;
import com.maverick.imodel.ISisterModel;
import com.maverick.model.CollectModel;
import com.maverick.model.DingCaiModel;
import com.maverick.model.SisterModel;
import com.maverick.presenter.implView.ISisterItemFragmentView;

import java.util.HashMap;
import java.util.List;

import cntv.greendaolibrary.dbbean.DingCai;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterItemFragmentPresenter extends BasePresenter {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private ISisterItemFragmentView mView;
    private final ISisterModel mModel;
    private int mPage = 1;
    private SisterTabInfo mSisterTabInfo;

    public SisterItemFragmentPresenter(Context context, ISisterItemFragmentView view) {
        this.mContext = context;
        this.mView = view;
        this.mModel = new SisterModel();
    }

    @Override
    public void release() {
        if (mModel != null) {
            mModel.release();
        }
    }

    public void refreshData(SisterTabInfo sisterTabInfo) {
        mSisterTabInfo = sisterTabInfo;

        if (sisterTabInfo == null) {
            mView.onShowErrorView();
            return;
        }

        mModel.requestData(sisterTabInfo.getType(), sisterTabInfo.getKey(), 1, new ISisterModel.OnResultListener() {

            @Override
            public void onSuccess(List<SisterInfo> list) {
                if (list != null && list.size() >= 1) {
                    for (int i = 0; i < list.size(); i++) {
                        Log.e(TAG, "" + list.get(i).getImage2());
                        SisterInfo sisterInfo = list.get(i);

                        sisterInfo.setCollect(CollectModel.newInstance().hasCollectDB(BeanHelper.getCollect(sisterInfo)));

                        DingCai dingCai = new DingCai();
                        dingCai.setDingCaiId(sisterInfo.getId());

                        dingCai = DingCaiModel.newInstance().getSisterDingCai(dingCai);
                        if (dingCai == null) {
                            continue;
                        }

                        if (dingCai.getDing()) {
                            sisterInfo.setDing(true);
                            sisterInfo.setCai(false);
                        } else {
                            sisterInfo.setDing(false);
                            sisterInfo.setCai(dingCai.getCai());
                        }
                    }
                    mView.onShowSuccessView(list);
                } else {
                    mView.onShowEmptyView();
                }
                mPage = 1;
            }

            @Override
            public void onFail() {
                mView.onShowErrorView();
            }
        });
    }

    /**
     * 服务器返回url，通过url去获取视频的第一帧
     * Android 原生给我们提供了一个MediaMetadataRetriever类
     * 提供了获取url视频第一帧的方法,返回Bitmap对象
     *
     * @param videoUrl
     * @return
     */
    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    public void loadMoreData() {

        if (mSisterTabInfo == null) {
            mView.onLoadMoreFail();
            return;
        }

        mModel.requestData(mSisterTabInfo.getType(), mSisterTabInfo.getKey(), mPage + 1, new ISisterModel.OnResultListener() {

            @Override
            public void onSuccess(List<SisterInfo> list) {
                //此接口固定20条数据
                mView.onLoadMoreSuccess(list, list.size() >= 20);
                mPage++;
            }

            @Override
            public void onFail() {
                mView.onLoadMoreFail();
            }
        });
    }

    public int getRandom(String ding) {
        int dingCount = getString2Int(ding);
        return (int) (Math.random() * dingCount);
    }

    public int getString2Int(String str) {

        try {
            return Integer.parseInt(str);
        } catch (Exception e) {

        }
        return 0;
    }
}
