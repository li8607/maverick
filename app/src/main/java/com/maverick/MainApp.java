package com.maverick;

import android.app.Application;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

/**
 * Created by ll on 2017/5/22.
 */
public class MainApp extends Application {
    {
        PlatformConfig.setWeixin("wx766193210f047aa3", "dacea00bc9908f50c730bb22f7528b25");
        PlatformConfig.setQQZone("1106104545", "gETKtj5JEd00Zsgn");
        PlatformConfig.setSinaWeibo("1053257822", "6a139b9cddd26bcfd78a770afd23c19f", "http://www.wandoujia.com/apps/com.maverick");

    }

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = false;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);

        MobclickAgent.openActivityDurationTrack(false);
    }
}
