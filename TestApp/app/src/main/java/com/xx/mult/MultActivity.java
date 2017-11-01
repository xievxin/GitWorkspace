package com.xx.mult;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import art.xx.com.testapp.R;

/**
 * Created by xievxin on 2017/9/7.
 */

public class MultActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.model);
        findViewById(R.id.webview).setBackgroundColor(Color.RED);
    }
}
