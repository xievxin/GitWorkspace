package com.xievxin.gtapps;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppDetailActivity extends Activity implements View.OnClickListener{

    private static final String TAG = "AppDetailActivity";

    String pkgName;
    final String staticActName = "com.igexin.sdk.GActivity";
    String dynActName = null;
    public static final String staticSvcName = "com.igexin.sdk.PushService";
    String dynSvcName = null;

    Button killBtn;
    Button staticActBtn;
    Button dynActBtn;
    Button staticSvcBtn;
    Button dynSvcBtn;
    TextView stateTv;
    EditText customEt;
    Button startActBtn;
    Button startSvcBtn;

    int enableFlag = 0x00;
    boolean schEnabled = true;
    Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pkgName = getIntent().getStringExtra("name");
        dynActName = AppFinder.getDynamicActName(this, pkgName);
        dynSvcName = AppFinder.getDynamicSvcName(pkgName);

        initView();
        fillData();
    }

    void fillData() {
        staticActBtn.setText(staticActBtn.getText() + ": " + staticActName);
        dynActBtn.setText(dynActBtn.getText() + ": " + dynActName);
        staticSvcBtn.setText(staticSvcBtn.getText() + ": " + staticSvcName);
        dynSvcBtn.setText(dynSvcBtn.getText() + ": " + dynSvcName);
    }

    void initView() {
        killBtn = findViewById(R.id.killBtn);
        staticActBtn = findViewById(R.id.staticActBtn);
        dynActBtn = findViewById(R.id.dynActBtn);
        staticSvcBtn = findViewById(R.id.staticSvcBtn);
        dynSvcBtn = findViewById(R.id.dynSvcBtn);
        stateTv = findViewById(R.id.stateTv);
        customEt = findViewById(R.id.customEt);
        startActBtn = findViewById(R.id.startActBtn);
        startSvcBtn = findViewById(R.id.startSvcBtn);

        killBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toKillProcess();
            }
        });
        staticActBtn.setOnClickListener(this);
        dynActBtn.setOnClickListener(this);
        staticSvcBtn.setOnClickListener(this);
        dynSvcBtn.setOnClickListener(this);
        startActBtn.setOnClickListener(cusOnclickListener);
        startSvcBtn.setOnClickListener(cusOnclickListener);

        customEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()>0) {
                    startActBtn.setEnabled(true);
                    startSvcBtn.setEnabled(true);
                }else {
                    startActBtn.setEnabled(false);
                    startSvcBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    void setBtnEnabled(boolean enabled) {
        staticActBtn.setEnabled(enabled);
        dynActBtn.setEnabled(enabled);
        staticSvcBtn.setEnabled(enabled);
        dynSvcBtn.setEnabled(enabled);

        if(enabled && TextUtils.isEmpty(customEt.getText().toString())) {
            return;
        }
        startActBtn.setEnabled(enabled);
        startSvcBtn.setEnabled(enabled);
    }

    @Override
    public void onClick(final View v) {
        dealOnClick(new DealListener() {
            @Override
            public boolean onHandleMain() {
                switch (v.getId()) {
                    case R.id.staticActBtn:
                        openActivity(staticActName);
                        break;
                    case R.id.dynActBtn:
                        if(TextUtils.isEmpty(dynActName)) {
                            Toast.makeText(AppDetailActivity.this, "不存在", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        openActivity(dynActName);
                        break;
                    case R.id.staticSvcBtn:
                        openService(staticSvcName);
                        break;
                    case R.id.dynSvcBtn:
                        if(TextUtils.isEmpty(dynSvcName)) {
                            Toast.makeText(AppDetailActivity.this, "不存在", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        openService(dynSvcName);
                        break;
                    default:break;
                }
                return true;
            }
        });
    }

    View.OnClickListener cusOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            dealOnClick(new DealListener() {
                @Override
                public boolean onHandleMain() {
                    String cls = customEt.getText().toString();
                    if(v==startActBtn) {
                        openActivity(cls);
                    }else if(v==startSvcBtn) {
                        openService(cls);
                    }
                    return true;
                }
            });
        }
    };

    void dealOnClick(DealListener dealListener) {
        synchronized (AppDetailActivity.class) {
            if (!schEnabled) {
                Toast.makeText(AppDetailActivity.this, "正在查询，请稍后", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!dealListener.onHandleMain()) {
                return;
            }

            setBtnEnabled(false);
            schEnabled = false;
        }

        waitingSearch();
    }

    final int waitTimes = 4;
    int waitCount;
    void waitingSearch() {
        waitCount = waitTimes;
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(waitCount--==0) {
                    stateTv.setText("正在查询......");
                    startSearch();
                    return;
                }
                stateTv.setText(waitCount+"s后开始查询");
                handler.postDelayed(this, 1000);
            }
        });
    }

    void startSearch() {
        boolean flag = AppFinder.isServiceWorked(this, pkgName, dynSvcName)
                || AppFinder.isServiceWorked(this, pkgName, staticSvcName);
        stateTv.setText(flag?"启动成功":"启动失败");

        setBtnEnabled(true);
        synchronized (AppDetailActivity.class) {
            schEnabled = true;
        }
    }

    boolean openActivity(String cls) {
        try {
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName(pkgName, cls);
            intent.setComponent(componentName);
            startActivity(intent);
            return true;
        }catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    void openService(String cls) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(pkgName, cls);
        intent.setComponent(componentName);

//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        }else {
            startService(intent);
//        }
    }

    void toKillProcess() {
        if(TextUtils.isEmpty(pkgName)) {
            Toast.makeText(this, "包名为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        mIntent.setData(Uri.fromParts("package", pkgName, null));
        startActivity(mIntent);
    }

    void killByMySelf() {
        BufferedReader reader = null;
        String content = "";
        try {
            java.lang.Process process = Runtime.getRuntime().exec("ps | grep " + pkgName + " | awk '{print $2}' | xargs kill -9");
//            Process process = Runtime.getRuntime().exec("ps | grep " + pkgName);
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuffer output = new StringBuffer();
            int read;
            char[] buffer = new char[4096];
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            content = output.toString();
            Log.i(TAG, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            setResult(1);
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    interface DealListener {
        boolean onHandleMain();
    }
}
