package com.xx.ndk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    private static final String TAG = "~";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new JniTest().sayDog();
        Log.i(TAG, "onCreate: "+JniTest.getHW());
    }

}
