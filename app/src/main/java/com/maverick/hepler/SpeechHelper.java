package com.maverick.hepler;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.maverick.MainApp;
import com.maverick.global.SPKey;
import com.maverick.util.PreferenceUtil;

/**
 * Created by limingfei on 2018/3/8.
 */

public class SpeechHelper {

    private static SpeechSynthesizer mTts;

    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 默认发音人
    private String voicer = "xiaoyan";
    private Context mContext;
    private boolean pause;
    private static SpeechHelper mSpeechHelper;

    public static SpeechHelper newInstance() {
        if (mSpeechHelper == null) {
            mSpeechHelper = new SpeechHelper(MainApp.mContext);
        }
        return mSpeechHelper;
    }

    public SpeechHelper(Context context) {
        this.mContext = context;
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);
        voicer = PreferenceUtil.getInstance().getString(SPKey.VOICER, "xiaoyan");
        // 设置参数
        setParam();
    }

    public void setParamVoicer(String voicer) {
        // 设置在线合成发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
        mTts.stopSpeaking();
        if (mOnSpeechListener != null) {
            mOnSpeechListener.onStopSpeaking();
        }
    }

    public void setParamSpeed(int speed) {
        mTts.setParameter(SpeechConstant.SPEED, String.valueOf(speed));
        mTts.stopSpeaking();
        if (mOnSpeechListener != null) {
            mOnSpeechListener.onStopSpeaking();
        }
    }

    public void setParamPitch(int pitch) {
        mTts.setParameter(SpeechConstant.PITCH, String.valueOf(pitch));
        mTts.stopSpeaking();
        if (mOnSpeechListener != null) {
            mOnSpeechListener.onStopSpeaking();
        }
    }

    public void setParamVolume(int volume) {
        mTts.setParameter(SpeechConstant.VOLUME, String.valueOf(volume));
        mTts.stopSpeaking();
        if (mOnSpeechListener != null) {
            mOnSpeechListener.onStopSpeaking();
        }
    }

    public void setParamKeyRequestFocus(boolean keyRequestFocus) {
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, String.valueOf(keyRequestFocus));
        mTts.stopSpeaking();
        if (mOnSpeechListener != null) {
            mOnSpeechListener.onStopSpeaking();
        }
    }

    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, String.valueOf(PreferenceUtil.getInstance().getInt(SPKey.SPEED, 50)));
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, String.valueOf(PreferenceUtil.getInstance().getInt(SPKey.PITCH, 50)));
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, String.valueOf(PreferenceUtil.getInstance().getInt(SPKey.VOLUME, 50)));
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }

        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
//        mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, String.valueOf(PreferenceUtil.getInstance().getBoolean(SPKey.KEY_REQUEST_FOCUS, true)));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
    }

    public void startSpeaking(String text) {
        int code = mTts.startSpeaking(text, mTtsListener);
        if (code != ErrorCode.SUCCESS) {
            Toast.makeText(mContext, "语音合成失败,错误码: " + code, Toast.LENGTH_SHORT).show();
        }
    }

    public void pauseSpeaking() {
        mTts.pauseSpeaking();
    }

    public void resumeSpeaking() {
        mTts.resumeSpeaking();
    }

    public void stopSpeaking() {
        mTts.stopSpeaking();
    }

    public boolean isSpeaking() {
        return mTts.isSpeaking();
    }

    public boolean isPause() {
        return pause;
    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            pause = false;
            if (mOnSpeechListener != null) {
                mOnSpeechListener.onSpeakBegin();
            }
        }

        @Override
        public void onSpeakPaused() {
            pause = true;
            if (mOnSpeechListener != null) {
                mOnSpeechListener.onSpeakPaused();
            }
        }

        @Override
        public void onSpeakResumed() {
            pause = false;
            if (mOnSpeechListener != null) {
                mOnSpeechListener.onSpeakResumed();
            }
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            if (mOnSpeechListener != null) {
                mOnSpeechListener.onBufferProgress(percent, beginPos, endPos);
            }
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            if (mOnSpeechListener != null) {
                mOnSpeechListener.onSpeakProgress(percent, beginPos, endPos);
            }
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                if (mOnSpeechListener != null) {
                    mOnSpeechListener.onCompleted();
                }
            } else if (error != null) {
                Toast.makeText(mContext, error.getPlainDescription(true), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(mContext, "初始化失败,错误码：" + code, Toast.LENGTH_SHORT).show();
            } else {
                if (mOnSpeechInitListener != null) {
                    mOnSpeechInitListener.onInit();
                }
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    private OnSpeechInitListener mOnSpeechInitListener;

    public void setOnSpeechInitListener(OnSpeechInitListener listener) {
        this.mOnSpeechInitListener = listener;
    }

    public interface OnSpeechInitListener {
        void onInit();
    }

    private OnSpeechListener mOnSpeechListener;

    public void setOnSpeechListener(OnSpeechListener listener) {
        this.mOnSpeechListener = listener;
    }

    public interface OnSpeechListener {

        void onSpeakBegin();

        void onSpeakPaused();

        void onSpeakResumed();

        void onBufferProgress(int percent, int beginPos, int endPos);

        void onSpeakProgress(int percent, int beginPos, int endPos);

        void onCompleted();

        void onStopSpeaking();
    }
}
