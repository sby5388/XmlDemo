package com.by5388.demo.xml;

import android.app.Application;
import android.os.Handler;
import android.os.HandlerThread;


/**
 * @author Administrator  on 2021/4/15.
 */
public class XmlApplication extends Application {
    public static final String TAG = "XmlApplication";
    private static XmlApplication sApplication;
    private Handler mWorkHandler;
    private HandlerThread mHandlerThread;

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
    }

    public Handler getWorkHandler() {
        return mWorkHandler;
    }

    public HandlerThread getHandlerThread() {
        return mHandlerThread;
    }
}
