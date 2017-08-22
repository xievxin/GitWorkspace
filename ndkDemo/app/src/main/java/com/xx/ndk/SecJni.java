package com.xx.ndk;

/**
 * Created by xievxin on 2017/8/22.
 */

public class SecJni {

    static {
        System.loadLibrary("firstJni");
    }

    public static native String ssss();

}
