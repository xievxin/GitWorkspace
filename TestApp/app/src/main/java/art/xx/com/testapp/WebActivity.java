package art.xx.com.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;


/**
 * Created by xievxin on 2017/6/28.
 */

public class WebActivity extends Activity {

    private static final String TAG = "WebActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.model);

        WebView webView = (WebView) findViewById(R.id.webview);
        Integer.parseInt("a");
        webView.addJavascriptInterface(new JsFunctions(), "js");

        String url = getIntent().getStringExtra("url");
        Log.i(TAG, "onCreate: "+url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/index.html");
    }

    private void crash() {
        throw new NullPointerException();
    }

    class JsFunctions {

        /**
         * 空方法，校验H5是不是App打开
         */
        @JavascriptInterface
        public void crash() {
            WebActivity.this.crash();
        }
    }
}
