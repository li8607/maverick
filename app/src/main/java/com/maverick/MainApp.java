package com.maverick;

import android.app.Application;
import android.app.UiModeManager;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.maverick.global.SPKey;
import com.maverick.global.ThemeType;
import com.maverick.util.PreferenceUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;


/**
 * Created by ll on 2017/5/22.
 */
public class MainApp extends Application {

    private static MainApp mMainApp;

    {
        PlatformConfig.setWeixin("wx766193210f047aa3", "dacea00bc9908f50c730bb22f7528b25");
        PlatformConfig.setQQZone("1106104545", "gETKtj5JEd00Zsgn");
        PlatformConfig.setSinaWeibo("1053257822", "6a139b9cddd26bcfd78a770afd23c19f", "http://www.wandoujia.com/apps/com.maverick");
    }

    public static Context mContext;

    @Override
    public void onCreate() {
        initTheme();
        super.onCreate();
        mMainApp = this;
        mContext = getApplicationContext();

        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = false;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);

        MobclickAgent.openActivityDurationTrack(false);

        AVOSCloud.initialize(this, "0C89a76qNodEmSHBpCSTGgCX-gzGzoHsz", "2Bcw0fzmDjYF6WXqEJumCKxR");
        AVOSCloud.setDebugLogEnabled(true);
        AVAnalytics.enableCrashReport(this, true);

        SpeechUtility.createUtility(mContext, SpeechConstant.APPID +"=5a9fb98c");
    }

    public int getCustomTheme() {
        int theme = PreferenceUtil.getInstance(getApplicationContext()).getInt(SPKey.THEME, 0);
        switch (theme) {
            case ThemeType.PINK:
                return R.style.AppTheme_Pink;
            case ThemeType.RED:
                return R.style.AppTheme_Red;
            case ThemeType.YELLOW:
                return R.style.AppTheme_Yellow;
            case ThemeType.GREEN:
                return R.style.AppTheme_Green;
            case ThemeType.BLUE:
                return R.style.AppTheme_Blue;
            case ThemeType.PURPLE:
                return R.style.AppTheme_Purple;
            default:
                return R.style.AppTheme_Pink;
        }
    }

    private void initTheme() {
        int modeTheme = PreferenceUtil.getInstance(getApplicationContext()).getInt(SPKey.NIGHT, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        if (modeTheme == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(modeTheme);
            UiModeManager mUiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
            mUiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
        } else if (modeTheme == AppCompatDelegate.MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(modeTheme);
            UiModeManager mUiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
            mUiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
        } else if (modeTheme == AppCompatDelegate.MODE_NIGHT_AUTO) {
            AppCompatDelegate.setDefaultNightMode(modeTheme);
            UiModeManager mUiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
            mUiModeManager.setNightMode(UiModeManager.MODE_NIGHT_AUTO);
        }
    }

    public static MainApp getInstance() {
        if (mMainApp == null) {
            mMainApp = new MainApp();
        }
        return mMainApp;
    }
}
