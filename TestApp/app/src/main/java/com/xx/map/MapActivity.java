package com.xx.map;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;


/**
 * Created by xievxin on 2017/8/7.
 */

public class MapActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TreeMap<String, String> arrayMap = new TreeMap<>();
        arrayMap.put("b", "x");
        arrayMap.put("a", "x");
        arrayMap.put("c", "x");
        arrayMap.put("d", "x");
    }
}
