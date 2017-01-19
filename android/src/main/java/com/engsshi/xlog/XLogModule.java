package com.engsshi.xlog;

import android.content.Context;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;

/**
 * Created by Sean.Ye on 2017/1/6.
 */

public class XLogModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    private static XLogSetting sXLogSetting;
    private static boolean sIsLogOpen = false;

    public XLogModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    /**
     * log include JS fatal exception and native uncaught exception
     */
    public static void initWithNativeCrashInclude(XLogSetting setting, Context context) {
        init(setting, context);
    }

    /**
     * JS soft exception log and native log
     */
    public static void init(XLogSetting setting) {
        init(setting, null);
    }

    private static void init(XLogSetting setting, Context context) {
        // https://github.com/Tencent/mars#android
        System.loadLibrary("stlport_shared");
        System.loadLibrary("marsxlog");
        sXLogSetting = setting;
        Log.setLogImp(new Xlog());

        FLog.setLoggingDelegate(new FLogging2XLogDelegate()); // 重定向RN原生log(FLog)到xlog
        FLog.setMinimumLoggingLevel(setting.getLevel());// FLog的日志级别和xlog一致

        if (context != null) {
            XLogCustomCrashHandler.getInstance().register(context); // 捕获全局的crash信息
        }
    }

    /**
     * turn on log
     */
    @ReactMethod
    public static void open() {
        if (sIsLogOpen)
            return;

        Xlog.appenderOpen(
                sXLogSetting.getLevel(),
                sXLogSetting.getAppenderMode(),
                sXLogSetting.getCacheDir(),
                sXLogSetting.getPath(),
                sXLogSetting.getNamePrefix());
        Xlog.setConsoleLogOpen(sXLogSetting.isOpenConsoleLog());

        sIsLogOpen = true;
    }

    /**
     * turn off log
     */
    @ReactMethod
    public static void close() {
        Log.appenderClose();
        sIsLogOpen = false;
    }

    /**
     * log
     *
     * @param level   log level
     * @param tag     Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    @ReactMethod
    public static void log(int level, String tag, String message) {
        switch (level) {
            case Log.LEVEL_VERBOSE:
                Log.v(tag, message);
                break;

            case Log.LEVEL_DEBUG:
                Log.d(tag, message);
                break;

            case Log.LEVEL_INFO:
                Log.i(tag, message);
                break;

            case Log.LEVEL_WARNING:
                Log.w(tag, message);
                break;

            case Log.LEVEL_ERROR:
                Log.e(tag, message);
                break;

            case Log.LEVEL_FATAL:
                Log.f(tag, message);
                break;

            default: //log none
                break;
        }
    }

    @Override
    public String getName() {
        return "XLogBridge";
    }

    @Override
    public void onHostResume() {

    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostDestroy() {
        Log.appenderFlush(false); //强制flush, 不等待flush结束就返回
    }
}
