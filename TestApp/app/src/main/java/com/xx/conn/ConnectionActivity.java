package com.xx.conn;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by xievxin on 2017/7/20.
 */

public class ConnectionActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        FormBody body = new FormBody.Builder().add("", "").build();
        Request request1 = new Request.Builder().post(body).build();


        final Request request = new Request.Builder()
                .url("https://www.baidu.com/")
                .build();

            new Thread(){
                @Override
                public void run() {
                    try {
                        Call call = new OkHttpClient().newCall(request);
                        call.execute();
                        req();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
    }

    private void req() {
        final Request request = new Request.Builder()
                .url("https://www.baidu.com/")
                .build();

        new Thread() {
            @Override
            public void run() {
                try {
                    Call call = new OkHttpClient().newCall(request);
                    call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
