package com.engsshi.example;

import android.os.Bundle;

import com.facebook.common.logging.FLog;
import com.facebook.react.ReactActivity;
import com.tencent.mars.xlog.Log;

public class MainActivity extends ReactActivity {
    public static final String TAG = MainActivity.class.getName();
    public static final String F_TAG = "F_" + MainActivity.class.getName();

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "example";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "if you open xlog in application's onCreate(), rather than delay to RNView, you may see me");
        Log.e(TAG, "if you open xlog in application's onCreate(), rather than delay to RNView, you may see me");
        Log.i(TAG, "if you open xlog in application's onCreate(), rather than delay to RNView, you may see me");
        Log.w(TAG, "if you open xlog in application's onCreate(), rather than delay to RNView, you may see me");
        Log.e(TAG, "if you open xlog in application's onCreate(), rather than delay to RNView, you may see me");
        Log.f(TAG, "if you open xlog in application's onCreate(), rather than delay to RNView, you may see me");
        Log.i(TAG, "if you open xlog in application's onCreate(), rather than delay to RNView, you may see me");

        FLog.v(F_TAG, "if you open xlog in application's onCreate(), rather than delay to RNView, you may see me");
        FLog.e(F_TAG, "if you open xlog in application's onCreate(), rather than delay to RNView, you may see me");
        FLog.i(F_TAG, "if you open xlog in application's onCreate(), rather than delay to RNView, you may see me");
        FLog.w(F_TAG, "if you open xlog in application's onCreate(), rather than delay to RNView, you may see me");
        FLog.e(F_TAG, "if you open xlog in application's onCreate(), rather than delay to RNView, you may see me");
        FLog.wtf(F_TAG, "if you open xlog in application's onCreate(), rather than delay to RNView, you may see me");

        android.util.Log.wtf("android-" + TAG, "you can't see me in xlog file");
    }
}
