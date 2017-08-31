package com.xx.ndk;

import android.util.Log;

/**
 * Created by xievxin on 2017/8/22.
 */

public class JniTest {

    static {
        System.loadLibrary("firstJni");
    }

    public static native String getHW();

    public native void sayDog();

    public void wangwang(String num) {
        Log.i("~", "wangwang: " + num + "声");
    }

}
