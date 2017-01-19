package com.engsshi.xlog;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;

import com.tencent.mars.xlog.Log;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Created by Sean.Ye on 2017/1/17.
 * <p>
 * 抓取原生部分全局的crash信息,打到xlog中
 * <p>
 * 从安卓处理JS传递过来的异常类{@link com.facebook.react.modules.core.ExceptionsManagerModule}，可以看到有两种异常：
 * {@link com.facebook.react.modules.core.ExceptionsManagerModule#reportFatalException}：直接抛出 JavascriptException
 * {@link com.facebook.react.modules.core.ExceptionsManagerModule#reportSoftException}：写入日志Flog.e(...)
 * 所以本类可以捕获到JS端的fatalException（即JavascriptException）
 */
public class XLogCustomCrashHandler implements UncaughtExceptionHandler {
    private static final String TAG = "XLogCustomCrashHandler";

    private static final XLogCustomCrashHandler XLOG_CUSTOM_CRASH_HANDLER = new XLogCustomCrashHandler();
    private UncaughtExceptionHandler mOldHandler;

    private PackageInfo mPackageInfo;
    private String mPackageName;

    public static XLogCustomCrashHandler getInstance() {
        return XLOG_CUSTOM_CRASH_HANDLER;
    }

    public void register(Context context) {
        this.setPackageInfo(context);

        mOldHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (mOldHandler != this) {
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            Log.e(TAG, "----------------------------System Information----------------------------");

            Log.e(TAG, "AppPkgName:" + mPackageInfo);
            if (mPackageInfo != null) {
                Log.e(TAG, "VersionCode:" + mPackageInfo.versionCode);
                Log.e(TAG, "VersionName:" + mPackageInfo.versionName);
                Log.e(TAG, "Debug:" + (0 != (mPackageInfo.applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE)));
            } else {
                Log.e(TAG, "VersionCode:-1");
                Log.e(TAG, "VersionName:null");
                Log.e(TAG, "Debug:un_known");
            }
            Log.e(TAG, "Board:" + Build.BOARD);
            Log.e(TAG, "ro.bootloader:" + Build.BOOTLOADER);
            Log.e(TAG, "ro.product.brand:" + Build.BRAND);
            Log.e(TAG, "ro.product.cpu.abi:" + Build.CPU_ABI);
            Log.e(TAG, "ro.product.cpu.abi2:" + Build.CPU_ABI2);
            Log.e(TAG, "ro.product.device:" + Build.DEVICE);
            Log.e(TAG, "ro.build.display.id:" + Build.DISPLAY);
            Log.e(TAG, "ro.build.fingerprint:" + Build.FINGERPRINT);
            Log.e(TAG, "ro.hardware:" + Build.HARDWARE);
            Log.e(TAG, "ro.build.host:" + Build.HOST);
            Log.e(TAG, "ro.build.id:" + Build.ID);
            Log.e(TAG, "ro.product.manufacturer:" + Build.MANUFACTURER);
            Log.e(TAG, "ro.product.model:" + Build.MODEL);
            Log.e(TAG, "ro.product.name:" + Build.PRODUCT);
            Log.e(TAG, "ro.build.tags:" + Build.TAGS);
            Log.e(TAG, "ro.build.type:" + Build.TYPE);
            Log.e(TAG, "ro.build.user:" + Build.USER);
            Log.e(TAG, "ro.build.version.codename:" + Build.VERSION.CODENAME);
            Log.e(TAG, "ro.build.version.incremental:" + Build.VERSION.INCREMENTAL);
            Log.e(TAG, "ro.build.version.release:" + Build.VERSION.RELEASE);
            Log.e(TAG, "ro.build.version.sdk:" + Build.VERSION.SDK_INT);

            Log.e(TAG, "----------------------------Exception----------------------------");
            Log.printErrStackTrace(TAG, ex, "----------------------------Exception StackTrace----------------------------\n");

        } finally {
            if (mOldHandler != null) {
                mOldHandler.uncaughtException(thread, ex);
            }
        }
    }

    private void setPackageInfo(Context context) {
        mPackageName = context.getPackageName();
        try {
            mPackageInfo = context.getPackageManager().getPackageInfo(mPackageName, 0);
        } catch (Exception e) {
            mPackageInfo = null;
        }
    }
}
