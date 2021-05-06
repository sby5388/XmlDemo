package com.by5388.demo.xml;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * @author Administrator  on 2021/4/15.
 */
public class XmlApplication extends Application {
    public static final String TAG = "XmlApplication";
    private static XmlApplication sApplication;
    private Handler mWorkHandler;
    private HandlerThread mHandlerThread;
    private int mActivityCount = 0;
    private MyActivityLifecycleCallbacks mActivityLifecycleCallbacks = new MyActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            super.onActivityCreated(activity, savedInstanceState);
            Log.d(TAG, "onActivityCreated: " + activity.getClass().getSimpleName());
            mActivityCount++;
            Log.d(TAG, "onActivityCreated: mActivityCount = " + mActivityCount);
        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            super.onActivityDestroyed(activity);
            Log.d(TAG, "onActivityDestroyed: " + activity.getClass().getSimpleName());
            mActivityCount--;
            Log.d(TAG, "onActivityDestroyed: mActivityCount = " + mActivityCount);
            if (mActivityCount == 0) {
                release();
                unregisterActivityLifecycleCallbacks(this);
                sApplication = null;
                // TODO: 2021/5/6 stop application
                System.exit(0);
            }
        }
    };

    public static XmlApplication getApplication() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandlerThread = new HandlerThread(TAG + "-worker");
        mHandlerThread.start();
        mWorkHandler = new Handler(mHandlerThread.getLooper());
        sApplication = this;
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    public Handler getWorkHandler() {
        return mWorkHandler;
    }

    public HandlerThread getHandlerThread() {
        return mHandlerThread;
    }

    private void release() {
        mWorkHandler.removeCallbacksAndMessages(null);
        mHandlerThread.quitSafely();
        mWorkHandler = null;
        mHandlerThread = null;
    }

    private static class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {

        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {

        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {

        }
    }
}
