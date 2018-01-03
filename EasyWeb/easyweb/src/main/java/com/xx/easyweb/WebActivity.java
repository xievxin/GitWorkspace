package com.xx.easyweb;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.xx.easyweb.manager.SystemBarTintManager;
import com.xx.easyweb.manager.WebStyleManager;
import com.xx.model.ServiceBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Constructor;

import static com.xx.model.WebBeans.TYPE_FINISH;
import static com.xx.model.WebBeans.TYPE_LOAD_DATA;
import static com.xx.model.WebBeans.TYPE_LOAD_URL;
import static com.xx.model.WebBeans.TYPE_POST_URL;
import static com.xx.model.WebBeans.TYPE_SET_CONTENT;

/**
 * Created by xievxin on 2017/11/8.
 */

public class WebActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "WebActivity";

    final H mH = new H();

    View containerLayout;
    TextView titleTv;
    View backView;
    View closeView;
    EasyWebview webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webcontainer);
        WebService.activityCount.addAndGet(1);

        intentServiceName = getIntent().getStringExtra("intentService");

        initStatusBar();
        initView();
        invokeAndAddWebview();

        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 状态栏
     */
    private void initStatusBar() {
        WebStyleManager.setAppStyle(this);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(Color.WHITE);
        }
    }

    /**
     * 控件初始化
     */
    private void initView() {
        containerLayout = findViewById(R.id.container);
        titleTv = (TextView) findViewById(R.id.title);
        backView = findViewById(R.id.back);
        closeView = findViewById(R.id.closeAll);

        titleTv.setText(getIntent().getStringExtra("title"));
        backView.setOnClickListener(this);
        closeView.setOnClickListener(this);
    }

    private void invokeAndAddWebview() {
        String webClassName = getIntent().getStringExtra("webview");
        Log.i(TAG, "invokeAndAddWebview: " + webClassName);
        try {
            Class webCls = Class.forName(webClassName);
            Constructor con = webCls.getConstructor(Context.class);
            webView = (EasyWebview) con.newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (webView == null) throw new RuntimeException("webView init err:" + webClassName);
        }

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.container);
        linearLayout.addView(webView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Subscribe(sticky = true)
    public final void onEventMainThread(final ServiceBean eventBean) {
        if(eventBean==null)
            return;
        EventBus.getDefault().removeStickyEvent(eventBean);

        mH.post(new Runnable() {
            @Override
            public void run() {
                handleEvent(eventBean);
            }
        });
    }

    private void handleEvent(ServiceBean bean) {
        int type = bean.getType();
        Log.i(TAG, "handleEvent: type_"+type);
        switch (type) {
            case TYPE_FINISH:
                finish();
//                exit();
                break;
            case TYPE_SET_CONTENT:
                RemoteViews remoteViews = (RemoteViews) bean.obj1;
                remoteViews.reapply(this, containerLayout);
                break;
            case TYPE_LOAD_URL:
                handleLoadUrl(bean.obj1.toString());
                break;
            case TYPE_POST_URL:
                handlePostUrl(bean.obj1.toString(), (byte[]) bean.obj2);
                break;
            case TYPE_LOAD_DATA:
                ContentValues values = bean.getValues();
                handleLoadData(values.getAsString("data"), values.getAsString("mimeType"), values.getAsString("encoding"));
                break;
            default:
                Log.e(TAG, "handleEvent: type err!! " + type);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebService.activityCount.decrementAndGet();
        if(webView!=null) {
            webView.destory();
        }
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().removeAllStickyEvents();  // 防止下次进来操作剩下的
        mH.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        if(v==backView) {
            if(webView.canGoback()) {
                webView.goBack();
            }else {
                exit();
            }
        }else if(v==closeView) {
            exit();
        }
    }

    private void exit() {
        startIntentService(EasyIntentService.Action_OnFinish);
        finish();
    }

    private String intentServiceName;
    private void startIntentService(String action) {
        if(TextUtils.isEmpty(intentServiceName)) {
            return;
        }
        try {
            Intent intent = new Intent(this, Class.forName(intentServiceName));
            intent.setAction(action);
            startService(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleLoadUrl(String url) {
        webView.loadUrl(url);
    }

    public void handlePostUrl(String url, byte[] bytes) {
        webView.postUrl(url, bytes);
    }

    public void handleLoadData(String data, String mimeType, String encoding) {
        webView.loadData(data, mimeType, encoding);
    }

    /**
     * Binder调用webview在不同线程，需放在主线程中处理
     */
    private class H extends Handler {

        /*public static final int LOAD_URL = 100;
        public static final int LOAD_DATA = 101;
        public static final int POST_URL = 102;

        @Override
        public void handleMessage(Message msg) {
            Bundle data = null;
            switch (msg.what) {
                case LOAD_URL:
                    handleLoadUrl(msg.obj.toString());
                    break;
                case LOAD_DATA:
                    data = msg.getData();
                    handleLoadData(data.getString("data"), data.getString("mimeType"), data.getString("encoding"));
                    break;
                case POST_URL:
                    data = msg.getData();
                    handlePostUrl(data.getString("url"), data.getByteArray("bytes"));
                    break;
                default:break;
            }
        }*/
    }
}
