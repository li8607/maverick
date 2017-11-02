package com.maverick.video;

import android.content.Context;
import android.util.AttributeSet;

import com.maverick.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by limingfei on 2017/11/2.
 */
public class MaverickVideoView extends StandardGSYVideoPlayer {

    public MaverickVideoView(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MaverickVideoView(Context context) {
        super(context);
    }

    public MaverickVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.sample_video;
    }
}
