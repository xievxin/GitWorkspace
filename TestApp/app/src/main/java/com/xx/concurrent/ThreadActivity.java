package com.xx.concurrent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ExecutorService;

/**
 * Created by xievxin on 2017/7/18.
 */

public class ThreadActivity extends Activity {


    private static final String TAG = "ThreadActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ThreadUtil.runTask();
    }



    static class ThreadUtil {
        static ExecutorService pool;

        static {
            pool = Executors.newFixedThreadPool(1);
        }

        public static void runTask() {
            int count = 0;
            while(count++ < 2) {
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run: "+Thread.currentThread().getId());
                    }
                });
            }
        }
    }
}
