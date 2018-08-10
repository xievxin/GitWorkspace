package com.xievxin.gtapps;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by xievxin on 2018/8/9
 */
@RunWith(AndroidJUnit4.class)
public class TestTest {

    @Test
    public void test1() {
        Log.i("!!!!", "test1: before()");
        Context context = InstrumentationRegistry.getContext();
        Log.i("!!!!", "test1: "+context);

        AppFinder.isServiceWorked(context, "", "a");
    }
}