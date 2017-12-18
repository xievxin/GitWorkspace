package com.xx.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xin on 2016/11/9.
 */
public class EasyThreadPool {

    private static ExecutorService pool = null;
    private static Handler mainHandler = null;

    static {
        pool = Executors.newCachedThreadPool();
    }

    public static void execute(Runnable runnable) {
        pool.execute(runnable);
    }

    public static Handler getMainHandler() {
        if(mainHandler==null) {
            synchronized (EasyThreadPool.class) {
                if(mainHandler==null) {
                    mainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return mainHandler;
    }
}
