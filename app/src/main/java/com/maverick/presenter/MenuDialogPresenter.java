package com.maverick.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.maverick.R;
import com.maverick.bean.MenuDetailInfo;
import com.maverick.bean.MenuItemInfo;
import com.maverick.model.CollectModel;
import com.maverick.presenter.implView.IMenuDialogView;
import com.maverick.type.MenuType;
import com.maverick.type.ShareType;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
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
public class MenuDialogPresenter extends BasePresenter implements UMShareListener {

    private String TAG = getClass().getSimpleName();
    private Activity mActivity;
    private IMenuDialogView mView;
    private SHARE_MEDIA share_media;

    public MenuDialogPresenter(Activity activity, IMenuDialogView view) {
        this.mActivity = activity;
        this.mView = view;
    }

    @Override
    public void release() {

    }

    public void loadShareData(MenuDetailInfo menuDetailInfo) {
        List<MenuItemInfo> mList = new ArrayList<>();
        boolean isInstallWeixin = UMShareAPI.get(mActivity).isInstall(mActivity, SHARE_MEDIA.WEIXIN);
        boolean isInstallWeixinCircle = UMShareAPI.get(mActivity).isInstall(mActivity, SHARE_MEDIA.WEIXIN_CIRCLE);
        Log.e(TAG, "isInstallWeixin = " + isInstallWeixin);
        Log.e(TAG, "isInstallWeixinCircle = " + isInstallWeixinCircle);
        if (isInstallWeixin) {
            MenuItemInfo weixin = new MenuItemInfo();
            weixin.setMenuType(MenuType.SHARE_WEIXIN);
            mList.add(weixin);

            MenuItemInfo weixin_circle = new MenuItemInfo();
            weixin_circle.setMenuType(MenuType.SHARE_WEIXIN_CIRCLE);
            mList.add(weixin_circle);
        }

        MenuItemInfo sina = new MenuItemInfo();
        sina.setMenuType(MenuType.SHARE_SINA);
        mList.add(sina);

        boolean isInstallQQ = UMShareAPI.get(mActivity).isInstall(mActivity, SHARE_MEDIA.QQ);
        Log.e(TAG, "isInstallQQ = " + isInstallQQ);
        if (isInstallQQ) {
            MenuItemInfo qzone = new MenuItemInfo();
            qzone.setMenuType(MenuType.SHARE_QZONE);
            mList.add(qzone);
        }

        mView.onShowShareView(mList);

        List<MenuItemInfo> send = new ArrayList<>();
        MenuItemInfo weixin = new MenuItemInfo();
        weixin.setMenuType(MenuType.SEND_COLLENT);
        if(menuDetailInfo != null) {
            weixin.setCollect(CollectModel.newInstance().hasCollectDB(menuDetailInfo.getCollect()));
        }
        send.add(weixin);
        mView.onShowSendView(send);
    }

    private void share(MenuDetailInfo menuDetailInfo) {

        if (mActivity == null || share_media == null || menuDetailInfo == null) {
            onError(share_media, new NullPointerException());
            return;
        }

        ShareAction shareAction = new ShareAction(mActivity);
        shareAction.setPlatform(share_media);
        shareAction.setCallback(this);

        switch (menuDetailInfo.getShareType()) {
            case ShareType.TEXT:
                if (!TextUtils.isEmpty(menuDetailInfo.getText())) {
                    shareAction.withText(menuDetailInfo.getText()).share();
                } else {
                    onError(share_media, new NullPointerException());
                }
                break;
            case ShareType.IMAGE:
                if (!TextUtils.isEmpty(menuDetailInfo.getImageurl())) {
                    Log.e(TAG, "分享的图片地址：" + menuDetailInfo.getImageurl());
                    UMImage imageurl = new UMImage(mActivity, menuDetailInfo.getImageurl());
                    imageurl.setThumb(new UMImage(mActivity, R.drawable.thumb));
                    shareAction.withMedia(imageurl).share();
                } else {
                    onError(share_media, new NullPointerException());
                }
                break;
            case ShareType.VIDEO:
                if (!TextUtils.isEmpty(menuDetailInfo.getVideourl())) {
                    UMVideo video = new UMVideo(menuDetailInfo.getVideourl());
                    video.setThumb(new UMImage(mActivity, R.drawable.video_play_normal));
                    video.setTitle(menuDetailInfo.getTitle());
                    video.setDescription(menuDetailInfo.getTitle());
                    shareAction.withMedia(video).share();
                } else {
                    onError(share_media, new NullPointerException());
                }
                break;
            case ShareType.IMAGE_TEXT:
                if (!TextUtils.isEmpty(menuDetailInfo.getWeburl())) {
                    UMWeb web = new UMWeb(menuDetailInfo.getWeburl());
                    if (!TextUtils.isEmpty(menuDetailInfo.getImageurl())) {
                        web.setThumb(new UMImage(mActivity, menuDetailInfo.getImageurl()));
                    } else {
                        web.setThumb(new UMImage(mActivity, R.mipmap.maverick_app_image));
                    }
                    web.setTitle(menuDetailInfo.getTitle());
                    web.setDescription(menuDetailInfo.getTitle());
                    shareAction.withMedia(web).share();
                } else {
                    menuDetailInfo.setShareType(ShareType.IMAGE_TEXT);
                    share(menuDetailInfo);
                }
                break;
            case ShareType.VIDEO_TEXT:
                if (!TextUtils.isEmpty(menuDetailInfo.getWeburl())) {
                    UMWeb web = new UMWeb(menuDetailInfo.getWeburl());
                    web.setThumb(new UMImage(mActivity, R.drawable.video_play_normal));
                    web.setTitle(menuDetailInfo.getTitle());
                    web.setDescription(menuDetailInfo.getTitle());
                    shareAction.withText(menuDetailInfo.getTitle()).withMedia(web).share();
                } else {
                    menuDetailInfo.setShareType(ShareType.VIDEO);
                    share(menuDetailInfo);
                }
                break;
            case ShareType.WEB:
                if (!TextUtils.isEmpty(menuDetailInfo.getWeburl())) {
                    UMWeb web = new UMWeb(menuDetailInfo.getWeburl());
                    if (!TextUtils.isEmpty(menuDetailInfo.getImageurl())) {
                        web.setThumb(new UMImage(mActivity, menuDetailInfo.getImageurl()));
                    } else {
                        web.setThumb(new UMImage(mActivity, R.mipmap.maverick_app_image));
                    }
                    web.setTitle(menuDetailInfo.getTitle());
                    web.setDescription(menuDetailInfo.getTitle());
                    shareAction.withMedia(web).share();
                } else {
                    onError(share_media, new NullPointerException());
                }
                break;

        }
    }

    public void shareWechat(MenuDetailInfo menuDetailInfo) {
        this.share_media = SHARE_MEDIA.WEIXIN;
        share(menuDetailInfo);
    }

    public void shareWxcircle(MenuDetailInfo menuDetailInfo) {
        this.share_media = SHARE_MEDIA.WEIXIN_CIRCLE;
        share(menuDetailInfo);
    }

    public void shareSina(MenuDetailInfo menuDetailInfo) {
        this.share_media = SHARE_MEDIA.SINA;
        share(menuDetailInfo);
    }

    public void shareQzone(MenuDetailInfo menuDetailInfo) {
        this.share_media = SHARE_MEDIA.QZONE;
        share(menuDetailInfo);
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
