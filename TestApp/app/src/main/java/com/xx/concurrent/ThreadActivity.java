package com.xx.concurrent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xievxin on 2017/7/18.
 */

public class ThreadActivity extends Activity {


    private static final String TAG = "ThreadActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        ThreadUtil.runTask();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ThreadUtil.runTask();
//            }
//        }, 5000);

//        SynchronousQueue queue = new SynchronousQueue<String>();
//        queue.offer("1");
//        ThreadUtil.runTask();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ThreadUtil.runTask();
//            }
//        }, 5000);
        final ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < 2; i++) {
            new Thread() {
                @Override
                public void run() {
                    lock.lock();
                    Log.i(TAG, "run: ");
                    try {
                        Thread.sleep(120_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }

                }
            }.start();
        }
    }



    static class ThreadUtil {
        static ScheduledExecutorService pool;

        static {
            pool = Executors.newScheduledThreadPool(1);
        }

        public static void runTask() {
            pool.execute(runnable);
            pool.schedule(runnable, 10_000, TimeUnit.MILLISECONDS);
            pool.schedule(runnable, 30_000, TimeUnit.MILLISECONDS);
            pool.schedule(runnable, 20_000, TimeUnit.MILLISECONDS);
        }

        static Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60_000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "run: "+Thread.currentThread().getId());
            }
        };
    }
}
