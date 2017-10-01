package com.maverick.adapter.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.maverick.R;
import com.maverick.bean.SisterInfo;
import com.maverick.util.GlideUtil;
import com.maverick.util.MyVideoThumbLoader;
import com.maverick.weight.MyImageView;
import com.maverick.weight.RatioImageView;

import java.util.HashMap;

import tv.danmaku.ijk.media.example.widget.media.IjkVideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;


/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterVideoHolder extends SisterTextHolder {

    private SisterInfo mSisterInfo;
    private final MyImageView image;
    private final ViewGroup video_root;
    private MyVideoThumbLoader mVideoThumbLoader;

    public SisterVideoHolder(View itemView) {
        super(itemView);
        image = (MyImageView) itemView.findViewById(R.id.image);

//        image.setOnClickListener(this);
        itemView.setOnClickListener(this);

        video_root = (ViewGroup) itemView.findViewById(R.id.video_root);

        mVideoThumbLoader = new MyVideoThumbLoader(itemView.getContext());
    }

    public void bindData(Context context, SisterInfo sisterInfo) {
        super.bindData(context, sisterInfo);
        this.mSisterInfo = sisterInfo;
        name.setText("（视频）");
        Log.e(TAG, "sisterInfo = " + sisterInfo.getVideo_uri());
        image.setTag(sisterInfo.getVideo_uri());
        mVideoThumbLoader.showThumbByAsynctack(sisterInfo.getVideo_uri(), image);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        openVideo();
    }

    private void openVideo() {
        IjkVideoView ijkVideoView = new IjkVideoView(video_root.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        video_root.addView(ijkVideoView, layoutParams);

        if (mSisterInfo.getVideo_uri() == null) {
            Log.e(TAG, "mSisterInfo.getVideo_uri() == null");
            return;
        }

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        Log.e(TAG, "mSisterInfo.getVideo_uri() = " + mSisterInfo.getVideo_uri());
        ijkVideoView.setVideoURI(Uri.parse(mSisterInfo.getVideo_uri()));
        ijkVideoView.start();
    }
}
