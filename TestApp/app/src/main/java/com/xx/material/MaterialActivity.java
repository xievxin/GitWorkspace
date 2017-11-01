package com.xx.material;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by xievxin on 2017/9/7.
 */

public class MaterialActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.material);

//        FrameLayout layout = (FrameLayout) findViewById(R.id.mLayout);


//        TextInputLayout textInput = (TextInputLayout) findViewById(R.id.textInput);
//        textInput.setError("邮箱错误！！！");

        QbSdk.initX5Environment(this, null);
        WebView webView = new WebView(this);
        setContentView(webView);

        webView.loadUrl("http://news.qq.com/a/20170913/013311.htm");
    }
}
