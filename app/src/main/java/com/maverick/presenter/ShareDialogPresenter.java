package com.maverick.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.maverick.R;
import com.maverick.bean.ShareInfo;
import com.maverick.bean.ShareItemInfo;
import com.maverick.presenter.implView.IShareDialogView;
import com.maverick.type.ShareType;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/15.
 */
public class ShareDialogPresenter extends BasePresenter implements UMShareListener {

    private String TAG = getClass().getSimpleName();
    private Activity mActivity;
    private IShareDialogView mView;
    private SHARE_MEDIA share_media;

    public ShareDialogPresenter(Activity activity, IShareDialogView view) {
        this.mActivity = activity;
        this.mView = view;
    }

    @Override
    public void release() {

    }

    public void loadShareData() {
        List<ShareItemInfo> shareItemInfos = new ArrayList<>();

        ShareItemInfo weixin = new ShareItemInfo();
        weixin.setShareType(ShareType.WEIXIN);
        weixin.setId(R.drawable.umeng_socialize_wechat);
        weixin.setTitle(mActivity.getResources().getString(R.string.share_wechat));
        shareItemInfos.add(weixin);

        ShareItemInfo weixin_circle = new ShareItemInfo();
        weixin_circle.setShareType(ShareType.WEIXIN_CIRCLE);
        weixin_circle.setId(R.drawable.umeng_socialize_wxcircle);
        weixin_circle.setTitle(mActivity.getResources().getString(R.string.share_wxcircle));
        shareItemInfos.add(weixin_circle);

        ShareItemInfo sina = new ShareItemInfo();
        sina.setShareType(ShareType.SINA);
        sina.setId(R.drawable.umeng_socialize_sina);
        sina.setTitle(mActivity.getResources().getString(R.string.share_sina));
        shareItemInfos.add(sina);

        ShareItemInfo qzone = new ShareItemInfo();
        qzone.setShareType(ShareType.QZONE);
        qzone.setId(R.drawable.umeng_socialize_qzone);
        qzone.setTitle(mActivity.getResources().getString(R.string.share_qzone));
        shareItemInfos.add(qzone);

        mView.onShowShareView(shareItemInfos);
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
            case ShareType.TEXT:
                if (!TextUtils.isEmpty(shareInfo.getText())) {
                    shareAction.withText(shareInfo.getText()).share();
                } else {
                    onError(share_media, new NullPointerException());
                }
                break;
            case ShareType.IMAGE:
                if (!TextUtils.isEmpty(shareInfo.getImageurl())) {
                    Log.e(TAG, "分享的图片地址：" + shareInfo.getImageurl());
                    UMImage imageurl = new UMImage(mActivity, shareInfo.getImageurl());
                    imageurl.setThumb(new UMImage(mActivity, R.drawable.thumb));
                    shareAction.withMedia(imageurl).share();
                } else {
                    onError(share_media, new NullPointerException());
                }
                break;
            case ShareType.VIDEO:
                if (!TextUtils.isEmpty(shareInfo.getVideourl())) {
                    UMVideo video = new UMVideo(shareInfo.getVideourl());
                    video.setThumb(new UMImage(mActivity, R.drawable.video_play_normal));
                    video.setTitle(shareInfo.getTitle());
                    video.setDescription(shareInfo.getTitle());
                    shareAction.withMedia(video).share();
                } else {
                    onError(share_media, new NullPointerException());
                }
                break;
            case ShareType.IMAGE_TEXT:
                if (!TextUtils.isEmpty(shareInfo.getWeburl())) {
                    UMWeb web = new UMWeb(shareInfo.getWeburl());
                    if (!TextUtils.isEmpty(shareInfo.getImageurl())) {
                        web.setThumb(new UMImage(mActivity, shareInfo.getImageurl()));
                    } else {
                        web.setThumb(new UMImage(mActivity, R.mipmap.maverick_app_image));
                    }
                    web.setTitle(shareInfo.getTitle());
                    web.setDescription(shareInfo.getTitle());
                    shareAction.withMedia(web).share();
                } else {
                    shareInfo.setShareType(ShareType.IMAGE_TEXT);
                    share(shareInfo);
                }
                break;
            case ShareType.VIDEO_TEXT:
                if (!TextUtils.isEmpty(shareInfo.getWeburl())) {
                    UMWeb web = new UMWeb(shareInfo.getWeburl());
                    web.setThumb(new UMImage(mActivity, R.drawable.video_play_normal));
                    web.setTitle(shareInfo.getTitle());
                    web.setDescription(shareInfo.getTitle());
                    shareAction.withText(shareInfo.getTitle()).withMedia(web).share();
                } else {
                    shareInfo.setShareType(ShareType.VIDEO);
                    share(shareInfo);
                }
                break;
            case ShareType.WEB:
                if (!TextUtils.isEmpty(shareInfo.getWeburl())) {
                    UMWeb web = new UMWeb(shareInfo.getWeburl());
                    if (!TextUtils.isEmpty(shareInfo.getImageurl())) {
                        web.setThumb(new UMImage(mActivity, shareInfo.getImageurl()));
                    } else {
                        web.setThumb(new UMImage(mActivity, R.mipmap.maverick_app_image));
                    }
                    web.setTitle(shareInfo.getTitle());
                    web.setDescription(shareInfo.getTitle());
                    shareAction.withMedia(web).share();
                } else {
                    onError(share_media, new NullPointerException());
                }
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
        mView.showProgress(true);
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        Toast.makeText(mActivity, "分享成功", Toast.LENGTH_SHORT).show();
        mView.showProgress(false);
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Toast.makeText(mActivity, "分享错误", Toast.LENGTH_SHORT).show();
        mView.showProgress(false);
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Toast.makeText(mActivity, "取消分享", Toast.LENGTH_SHORT).show();
        mView.showProgress(false);
    }

    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    private Bitmap returnBitmap(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }
}
