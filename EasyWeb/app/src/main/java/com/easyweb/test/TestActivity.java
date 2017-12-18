package com.easyweb.test;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

/**
 * Created by xievxin on 2017/12/4.
 */

public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        setContentView(webView);

//        webView.loadUrl("https://www.baidu.com/");
//        webView.loadUrl("launch://ckjr.xinge");

        Intent intent = new Intent();
//        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(Uri.parse("launch://ckjr"));
        startActivity(intent);
    }


    abstract class A {
        public  void test(){

        }
    }

    class B extends A {
    }
}
