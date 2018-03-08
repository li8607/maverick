package com.maverick.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.maverick.R;

/**
 * Created by limingfei on 2018/3/8.
 */

public class ControlSpeechView extends FrameLayout implements View.OnClickListener {

    private ImageView mIv_play_control_speech;
    private View mFl_next_control_speech;
    private View mFl_stop_control_speech;
    private View mFl_play_control_speech;
    private ProgressBar mPb_progress_control_speech;

    public ControlSpeechView(@NonNull Context context) {
        this(context, null);
    }

    public ControlSpeechView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ControlSpeechView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_control_specch, this);

        mFl_play_control_speech = findViewById(R.id.fl_play_control_speech);
        mFl_stop_control_speech = findViewById(R.id.fl_stop_control_speech);
        mFl_next_control_speech = findViewById(R.id.fl_next_control_speech);
        mIv_play_control_speech = findViewById(R.id.iv_play_control_speech);
        mPb_progress_control_speech = findViewById(R.id.pb_progress_control_speech);

        mFl_play_control_speech.setOnClickListener(this);
        mFl_stop_control_speech.setOnClickListener(this);
        mFl_next_control_speech.setOnClickListener(this);
    }

    public void stop() {
        mIv_play_control_speech.setSelected(false);
        mIv_play_control_speech.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        mPb_progress_control_speech.setProgress(0);
        mPb_progress_control_speech.setVisibility(INVISIBLE);
    }

    public void pause() {
        mIv_play_control_speech.setImageResource(R.drawable.ic_play_arrow_black_24dp);
    }

    public void start() {
        mIv_play_control_speech.setSelected(true);
        mIv_play_control_speech.setImageResource(R.drawable.ic_pause_black_24dp);
        mPb_progress_control_speech.setVisibility(VISIBLE);
    }

    public void setProgress(int progress) {
        mPb_progress_control_speech.setProgress(progress);
    }

    public int getProgress() {
        return mPb_progress_control_speech.getProgress();
    }

    public void setSecondaryProgress(int progress) {
        mPb_progress_control_speech.setSecondaryProgress(progress);
    }

    public int getSecondaryProgress() {
        return mPb_progress_control_speech.getSecondaryProgress();
    }

    public void setMaxProgress(int max) {
        mPb_progress_control_speech.setMax(max);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_play_control_speech:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onPlayClick();
                }
                break;
            case R.id.fl_stop_control_speech:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onStopClick();
                }
                break;
            case R.id.fl_next_control_speech:
                break;
        }
    }

    public interface OnItemClickListener {
        void onPlayClick();

        void onStopClick();
    }
}
