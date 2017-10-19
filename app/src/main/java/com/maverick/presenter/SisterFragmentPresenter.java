package com.maverick.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.maverick.bean.SisterTabInfo;
import com.maverick.bean.SisterInfo;
import com.maverick.imodel.ISisterFragmentModel;
import com.maverick.model.SisterDingCaiModel;
import com.maverick.model.SisterFragmentModel;
import com.maverick.presenter.implView.ISisterFragmentView;

import java.util.HashMap;
import java.util.List;

import cntv.greendaolibrary.dbbean.SisterDingCai;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterFragmentPresenter extends BasePresenter {

    private String TAG = getClass().getSimpleName();

    private Context mContext;
    private ISisterFragmentView mView;
    private final ISisterFragmentModel mModel;
    private int mPage = 1;
    private SisterTabInfo mSisterTabInfo;

    public SisterFragmentPresenter(Context context, ISisterFragmentView view) {
        this.mContext = context;
        this.mView = view;
        this.mModel = new SisterFragmentModel();
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

        mModel.requestData(sisterTabInfo.getType(), sisterTabInfo.getKey(), 1, new ISisterFragmentModel.OnResultListener() {

            @Override
            public void onSuccess(List<SisterInfo> list) {
                if (list != null && list.size() >= 1) {
                    for (int i = 0; i < list.size(); i++) {
                        Log.e(TAG, "" + list.get(i).getImage2());
                        SisterInfo sisterInfo = list.get(i);

                        SisterDingCai sisterDingCai = new SisterDingCai();
                        sisterDingCai.setDingCaiId(sisterInfo.getId());

                        sisterDingCai = SisterDingCaiModel.newInstance().getSisterDingCai(sisterDingCai);
                        if (sisterDingCai == null) {
                            continue;
                        }

                        if (sisterDingCai.getDing()) {
                            sisterInfo.setDing(true);
                            sisterInfo.setCai(false);
                        } else {
                            sisterInfo.setDing(false);
                            sisterInfo.setCai(sisterDingCai.getCai());
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

        mModel.requestData(mSisterTabInfo.getType(), mSisterTabInfo.getKey(), mPage + 1, new ISisterFragmentModel.OnResultListener() {

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
