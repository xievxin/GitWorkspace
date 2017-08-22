package com.xx.ndk;

/**
 * Created by xievxin on 2017/8/22.
 */

public class JniTest {

    static {
        System.loadLibrary("firstJni");
    }

    public static native String getHW();

    public static native String getNameDetail(String name);
}
