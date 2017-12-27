package com.maverick;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.maverick.base.BaseActivity;
import com.maverick.global.SPKey;
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

    private int modeTheme;
    private static MainApp mMainApp;

    {
        PlatformConfig.setWeixin("wx766193210f047aa3", "dacea00bc9908f50c730bb22f7528b25");
        PlatformConfig.setQQZone("1106104545", "gETKtj5JEd00Zsgn");
        PlatformConfig.setSinaWeibo("1053257822", "6a139b9cddd26bcfd78a770afd23c19f", "http://www.wandoujia.com/apps/com.maverick");

    }

    public static Context mContext;

    @Override
    public void onCreate() {
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

//        AVOSCloud.initialize(this,"0C89a76qNodEmSHBpCSTGgCX-gzGzoHsz", "2Bcw0fzmDjYF6WXqEJumCKxR");
//        AVOSCloud.setDebugLogEnabled(true);

        initTheme();
    }

    private void initTheme() {
        modeTheme = PreferenceUtil.getInstance(getApplicationContext()).getInt(SPKey.NIGHT, 0);
        if (modeTheme == 1) {
            //夜
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (modeTheme == 2) {
            //自动
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        } else {
            //日
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public static MainApp getInstance() {
        if (mMainApp == null) {
            mMainApp = new MainApp();
        }
        return mMainApp;
    }

    public int getModeTheme() {
        return modeTheme;
    }

    public void setModeTheme(BaseActivity activity, int modeTheme) {
        if (this.modeTheme == modeTheme || activity == null) {
            return;
        }
        this.modeTheme = modeTheme;
        if (modeTheme == 1) {
            //夜
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (modeTheme == 2) {
            //自动
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        } else {
            //日
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        PreferenceUtil.getInstance(getApplicationContext()).putInt(SPKey.NIGHT, modeTheme);
        activity.recreate();
    }
}
