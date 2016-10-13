package com.malviya.demowebview.applog;

import android.util.Log;

/**
 * Created by 23508 on 10/13/2016.
 */

public class AppLog {
    private static final String TAG_LOG = "malviya_log";
    private static boolean debug = true;

    public static void log(String msg) {
        if (debug)
            Log.d(TAG_LOG, msg);
    }

    public static void error(String msg) {
        Log.d(TAG_LOG, msg);
    }
}
