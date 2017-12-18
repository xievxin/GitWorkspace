package com.xx.easyweb;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by xievxin on 2017/12/11.
 * 2017-12-11 new一下又没事，直接用来UI同步判断
 */
public abstract class EasyIntentService extends IntentService {

    private static final String TAG = "EasyIntentService";

    public static final String Action_OnFinish = "Action_OnFinish";

    public EasyIntentService() {
        super("EasyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent!=null && !TextUtils.isEmpty(intent.getAction())) {
            onProcessIntent(intent);
        }
    }

    private void onProcessIntent(Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case Action_OnFinish:
                onFinish();
                break;
            default:break;
        }
    }

    public abstract void onFinish();

}
