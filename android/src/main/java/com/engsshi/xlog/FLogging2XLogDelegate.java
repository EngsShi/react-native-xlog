package com.engsshi.xlog;

import com.facebook.common.logging.LoggingDelegate;
import com.tencent.mars.xlog.Log;

/**
 * Created by Sean.Ye on 2017/1/17.
 * 定制RN的LoggingDelegate，将RN原生的Flog输出到xlog中，实现逻辑参考 {@link com.facebook.common.logging.FLogDefaultLoggingDelegate}
 */

public class FLogging2XLogDelegate implements LoggingDelegate {
    private int mMinimumLoggingLevel = android.util.Log.WARN;

    @Override
    public int getMinimumLoggingLevel() {
        return this.mMinimumLoggingLevel;
    }

    @Override
    public void setMinimumLoggingLevel(int level) {
        this.mMinimumLoggingLevel = level;
    }

    @Override
    public boolean isLoggable(int level) {
        return mMinimumLoggingLevel <= level;
    }

    @Override
    public void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    @Override
    public void v(String tag, String msg, Throwable tr) {
        Log.v(tag, msg + getStackTraceString(tr));
    }

    @Override
    public void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    @Override
    public void d(String tag, String msg, Throwable tr) {
        Log.d(tag, msg + getStackTraceString(tr));
    }

    @Override
    public void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    @Override
    public void i(String tag, String msg, Throwable tr) {
        Log.i(tag, msg + getStackTraceString(tr));
    }

    @Override
    public void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    @Override
    public void w(String tag, String msg, Throwable tr) {
        Log.w(tag, msg + getStackTraceString(tr));
    }

    @Override
    public void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    @Override
    public void e(String tag, String msg, Throwable tr) {
        Log.e(tag, msg + getStackTraceString(tr));
    }

    /**
     * refer to {@link com.facebook.common.logging.FLogDefaultLoggingDelegate#wtf(String, String)}
     * might crash the app.
     */
    @Override
    public void wtf(String tag, String msg) {
        Log.e(tag, msg);
    }

    /**
     * refer to {@link com.facebook.common.logging.FLogDefaultLoggingDelegate#wtf(String, String, Throwable)}
     * might crash the app.
     */
    @Override
    public void wtf(String tag, String msg, Throwable tr) {
        Log.e(tag, msg + getStackTraceString(tr));
    }

    @Override
    public void log(int priority, String tag, String msg) {
        switch (priority) {
            case Log.LEVEL_VERBOSE:
                Log.v(tag, msg);
                break;

            case Log.LEVEL_DEBUG:
                Log.d(tag, msg);
                break;

            case Log.LEVEL_INFO:
                Log.i(tag, msg);
                break;

            case Log.LEVEL_WARNING:
                Log.w(tag, msg);
                break;

            case Log.LEVEL_ERROR:
                Log.e(tag, msg);
                break;

            case Log.LEVEL_FATAL:
                Log.f(tag, msg);
                break;

            default: //log none
                break;
        }
    }

    private String getStackTraceString(Throwable tr) {
        return "  " + android.util.Log.getStackTraceString(tr);
    }
}
