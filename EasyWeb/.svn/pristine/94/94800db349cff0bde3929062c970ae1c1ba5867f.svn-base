package com.xx.easyweb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


@SuppressLint("JavascriptInterface")
public abstract class EasyWebview extends RelativeLayout {

    private static final String TAG = "EasyWebview";

    protected WebView webView;
    public Context context;
    private ProgressBar progressBar;
    private int _2dp;

    public EasyWebview(Context context) {
        this(context, null);
    }

    public EasyWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        _2dp = (int) getResources().getDimension(R.dimen.dividerHeight);

        initView();

        setWebCfg();

        onInit();
    }

    private void initView() {
        webView = new WebView(context);
        this.addView(webView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar));
        progressBar.setMax(100);
        LayoutParams progressP = new LayoutParams(LayoutParams.MATCH_PARENT, 2 * _2dp);
        progressP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        this.addView(progressBar, progressP);
    }

    private MyWebViewClient mWebClient = new MyWebViewClient();
    private void setWebCfg() {
        webView.setWebViewClient(mWebClient);
        webView.setWebChromeClient(new MyWebChromeClient());
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    public void loadData(String data, String mimeType, String encoding) {
        webView.loadData(data, mimeType, encoding);
    }

    public void postUrl(String url, byte[] bytes) {
        webView.postUrl(url, bytes);
    }

    public boolean canGoback() {
        return webView.canGoBack();
    }

    public void goBack() {
        if (canGoback())
            webView.goBack();
    }

    public void destory() {
        webView.destroy();
    }

    class MyWebViewClient extends WebViewClient {

//        private long pageStartTime;


        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return EasyWebview.this.shouldOverrideUrlLoading(view, url);
        }

        // ------------------------------Request Start------------------------------
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            return EasyWebview.this.shouldInterceptRequest(webView, webResourceRequest);
        }

        public WebResourceResponse superShouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            // 总之，最后会交给shouldInterceptRequest(webView, url) 中处理
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP) {
                return super.shouldInterceptRequest(webView, webResourceRequest.getUrl().toString());
            }
            return super.shouldInterceptRequest(webView, webResourceRequest);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
            return EasyWebview.this.shouldInterceptRequest(webView, url);
        }

        public WebResourceResponse superShouldInterceptRequest(WebView webView, String s) {
            return super.shouldInterceptRequest(webView, s);
        }
        // ------------------------------Request End------------------------------

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            pageStartTime = SystemClock.elapsedRealtime();
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);

            EasyWebview.this.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.INVISIBLE);
            EasyWebview.this.onPageFinished(view, url);
//            Log.i(TAG, "onPageFinished: use times: " + (SystemClock.elapsedRealtime()-pageStartTime) + "ms");
        }

    }

    class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            EasyWebview.this.onReceivedTitle(view, title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if(newProgress==100) {
                progressBar.setVisibility(View.INVISIBLE);
            }else {
                progressBar.setProgress(newProgress);
            }
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            return EasyWebview.this.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            return EasyWebview.this.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return EasyWebview.this.onJsPrompt(view, url, message, defaultValue, result);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        webView.destroy();
    }


    /**
     * 此方法由：web进程调用
     */
    public abstract void onInit();

    public final void addJavascriptInterface(Object obj, String jsName) {
        webView.addJavascriptInterface(obj, jsName);
    }

    public abstract boolean shouldOverrideUrlLoading(WebView view, String url);

    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        return mWebClient.superShouldInterceptRequest(webView, webResourceRequest);
    }

    public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
        return mWebClient.superShouldInterceptRequest(webView, url);
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) { }

    public void onPageFinished(WebView view, String url) { }

    public void onReceivedTitle(WebView view, String title) { }

    public abstract boolean onJsAlert(WebView view, String url, String message, final JsResult result);

    public abstract boolean onJsConfirm(WebView view, String url, String message, final JsResult result);

    public abstract boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result);
}
