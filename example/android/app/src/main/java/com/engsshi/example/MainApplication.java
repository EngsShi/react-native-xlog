package com.engsshi.example;

import android.app.Application;
import android.os.Environment;

import com.engsshi.xlog.XLogModule;
import com.engsshi.xlog.XLogPackage;
import com.engsshi.xlog.XLogSetting;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        protected boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.asList(
                    new MainReactPackage(),
                    new XLogPackage()
            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);


        final String appName = this.getString(R.string.app_name);
        final String logPath = Environment.getExternalStorageDirectory().getAbsolutePath() + '/' + appName + "/log";

        XLogSetting xLogSetting = XLogSetting.builder()
                .setLevel(XLogSetting.LEVEL_DEBUG)
                .setPath(logPath)
                .setCacheDir("")
                .setAppenderMode(XLogSetting.APPENDER_MODE_ASYNC)
                .setNamePrefix(appName)
                .setOpenConsoleLog(true)
                .build();
        XLogModule.initWithNativeCrashInclude(xLogSetting, this);

        XLogModule.open(); // 在app启动是打开xlog，捕获进入JS前的log
    }
}
