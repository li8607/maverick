package com.maverick.presenter;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.maverick.R;
import com.maverick.bean.ShareInfo;
import com.maverick.bean.SisterInfo;
import com.maverick.presenter.implView.IShareDialogView;
import com.maverick.type.ShareType;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.BaseMediaObject;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/15.
 */
public class ShareDialogPresenter extends BasePresenter implements UMShareListener {

    private Activity mActivity;
    private IShareDialogView mView;

    public List<String> styles = new ArrayList<String>();

    private SHARE_MEDIA share_media;

    public ShareDialogPresenter(Activity activity, IShareDialogView view) {
        this.mActivity = activity;
        this.mView = view;
    }

    @Override
    public void release() {

    }

    private void share(ShareInfo shareInfo) {

        if (mActivity == null || share_media == null || shareInfo == null) {
            onError(share_media, new NullPointerException());
            return;
        }

        ShareAction shareAction = new ShareAction(mActivity);
        shareAction.setPlatform(share_media);
        shareAction.setCallback(this);

        switch (shareInfo.getShareType()) {
            case ShareType.IMAGE_URL:
                UMImage imageurl = new UMImage(mActivity, shareInfo.getImageurl());
                imageurl.setThumb(new UMImage(mActivity, R.mipmap.maverick_app_image));
                imageurl.setTitle(shareInfo.getTitle());
                shareAction.withMedia(imageurl).share();
                break;
        }
    }

    public void shareWechat(ShareInfo shareInfo) {
        this.share_media = SHARE_MEDIA.WEIXIN;
        share(shareInfo);
    }

    public void shareWxcircle(ShareInfo shareInfo) {
        this.share_media = SHARE_MEDIA.WEIXIN_CIRCLE;
        share(shareInfo);
    }

    public void shareSina(ShareInfo shareInfo) {
        this.share_media = SHARE_MEDIA.SINA;
        share(shareInfo);
    }

    public void shareQzone(ShareInfo shareInfo) {
        this.share_media = SHARE_MEDIA.QZONE;
        share(shareInfo);
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        mView.showProgress();
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        Toast.makeText(mActivity, "分享成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Toast.makeText(mActivity, "分享错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Toast.makeText(mActivity, "取消分享", Toast.LENGTH_SHORT).show();
    }


}
