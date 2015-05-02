package com.wecamchat.aviddev.util;

import android.util.Log;

/**
 * Created by tsingh on 27/1/15.
 */
public class Logger {

    public static void ERROR(String tag, String msg, Throwable th){
        Log.e(tag, msg, th);
    }

    public void WARN(String tag, String msg, Throwable th){
        Log.w(tag, msg, th);
    }

    public void INFO(String tag, String msg, Throwable th){
        Log.i(tag, msg, th);
    }

    public void DEBUG(String tag, String msg, Throwable th){
        Log.d(tag, msg, th);
    }
}
