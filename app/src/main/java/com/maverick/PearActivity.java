package com.maverick;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.maverick.base.BaseActivity;
import com.maverick.bean.PearVideoDetailBean;
import com.maverick.bean.PearVideoDetailInfoVideo;
import com.maverick.fragment.PearBottomFragment;
import com.maverick.hepler.BeanHelper;
import com.maverick.model.HistoryModel;
import com.maverick.presenter.BasePresenter;
import com.maverick.video.SampleListener;
import com.maverick.video.SampleVideo;
import com.maverick.video.SwitchVideoModel;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import cntv.greendaolibrary.dbbean.History;

/**
 * Created by Administrator on 2017/10/31.
 */

public class PearActivity extends BaseActivity implements PearBottomFragment.OnListener {

    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    private NormalGSYVideoPlayer mVideo;
    private OrientationUtils orientationUtils;
    private PearVideoDetailBean mInfo;

    public static void launch(Activity activity, PearVideoDetailBean info) {

        if (activity == null || info == null) {
            return;
        }

        Intent intent = new Intent(activity, PearActivity.class);
        intent.putExtra(EXTRA_IMAGE, info);
        activity.startActivity(intent);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                View decorView = getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_pear;
    }

    @Override
    protected void onInitView() {
        mVideo = findView(R.id.video);
        mVideo.setRotateViewAuto(false);
        mVideo.setLockLand(false);
        mVideo.setShowFullAnimation(false);
        mVideo.setNeedLockFull(true);
        mVideo.setSeekRatio(1);

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.mipmap.ic_launcher);
        mVideo.setThumbImageView(imageView);

        //增加title
        mVideo.getTitleTextView().setVisibility(View.VISIBLE);
        //videoPlayer.setShowPauseCover(false);

        //videoPlayer.setSpeed(2f);

        //设置返回键
        mVideo.getBackButton().setVisibility(View.VISIBLE);

        //设置旋转
        orientationUtils = new OrientationUtils(this, mVideo);


//        //关闭自动旋转
//        mVideo.setRotateViewAuto(false);
//        mVideo.setLockLand(false);
//        mVideo.setShowFullAnimation(false);
//        mVideo.setNeedLockFull(true);

        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        mVideo.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

//                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
//                mVideo.startWindowFullscreen(PearActivity.this, true, true);
            }
        });

        //videoPlayer.setBottomProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_progress));
        //videoPlayer.setDialogVolumeProgressBar(getResources().getDrawable(R.drawable.video_new_volume_progress_bg));
        //videoPlayer.setDialogProgressBar(getResources().getDrawable(R.drawable.video_new_progress));
        //videoPlayer.setBottomShowProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_seekbar_progress),
        //getResources().getDrawable(R.drawable.video_new_seekbar_thumb));
        //videoPlayer.setDialogProgressColor(getResources().getColor(R.color.colorAccent), -11);

        //是否可以滑动调整
        mVideo.setIsTouchWiget(true);

        //设置返回按键功能
        mVideo.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mVideo.setStandardVideoAllCallBack(new SampleListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
                isPlay = true;
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
//                if (orientationUtils != null) {
//                    orientationUtils.backToProtVideo();
//                }
            }

            @Override
            public void onEnterFullscreen(String url, Object... objects) {
                super.onEnterFullscreen(url, objects);
                //隐藏调全屏对象的返回按键
//                GSYVideoPlayer gsyVideoPlayer = (GSYVideoPlayer)objects[1];
//                gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
            }
        });
    }

    private boolean isPlay;
    private boolean isPause;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && mVideo.getCurrentState() != SampleVideo.CURRENT_STATE_PAUSE) {
            mVideo.onConfigurationChanged(this, newConfig, orientationUtils);
        }
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mInfo = (PearVideoDetailBean) getIntent().getSerializableExtra(EXTRA_IMAGE);
        replaceFragment(R.id.bottom, PearBottomFragment.newInstance(mInfo));


        History history = BeanHelper.getHistory(mInfo);
        history = HistoryModel.newInstance().getHistoryDB(history);
        mVideo.setSeekOnStart(history != null ? (int) history.getHistoryVideoPosition() : 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideo.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideo.onVideoResume();
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            mVideo.getFullscreenButton().performClick();
            return;
        }

        History history = BeanHelper.getHistory(mInfo);
        history.setHistoryVideoPosition(GSYVideoManager.instance().getMediaPlayer().getCurrentPosition());
        HistoryModel.newInstance().insertHistoryDB(history);

        //释放所有
        mVideo.setStandardVideoAllCallBack(null);
        GSYVideoPlayer.releaseAllVideos();
        super.onBackPressed();
//        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            super.onBackPressed();
//        } else {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    finish();
//                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
//                }
//            }, 500);
//        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        //如果旋转了就全屏
////        mVideo.onConfigurationChanged(this, newConfig, orientationUtils);
//    }

    @Override
    public void playVideo(List<PearVideoDetailInfoVideo> list) {
        if (list == null || list.size() < 1) {
            return;
        }

        List<SwitchVideoModel> models = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            PearVideoDetailInfoVideo info = list.get(i);

            if (TextUtils.isEmpty(info.getUrl())) {
                continue;
            }

            String source1 = info.getUrl();
            String name = mInfo == null ? "" : mInfo.getName();
            SwitchVideoModel model = new SwitchVideoModel(name, source1);
            models.add(model);
        }

        if (models != null && models.size() > 0) {
            mVideo.setUp(models.get(0).getUrl(), true, models.get(0).getName());
            mVideo.startPlayLogic();
        }
    }
}
