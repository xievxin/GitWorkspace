package com.xx.easyweb.manager;

import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.xx.easyweb.EasyIntentService;
import com.xx.easyweb.EasyWebview;
import com.xx.easyweb.IWeb;
import com.xx.easyweb.WebService;
import com.xx.listener.OnServiceConnectListener;


/**
 * Created by xievxin on 2017/11/8.
 */

public class WebServiceManager {

    private static final String TAG = "WebServiceManager";

    private static WebServiceManager mInstance = null;
    IWeb webService;
    private OnServiceConnectListener mOnServiceConnectListener;
    private Context mContext;
    private static String webClassName;
    private static String mIntentServiceName;

    private WebServiceManager() {
    }

    public static WebServiceManager getInstance() {
        if(mInstance==null) {
            synchronized (WebServiceManager.class) {
                if(mInstance==null)
                    mInstance = new WebServiceManager();
            }
        }
        return mInstance;
    }

    public static<T extends EasyWebview> void bindWebService(Class<T> webClass) {
        checkService(webClass);
        WebServiceManager.webClassName = webClass.getName();
    }

    public static<T extends EasyIntentService> void bindIntentService(Class<T> mIntentServiceClass) {
        checkService(mIntentServiceClass);
        WebServiceManager.mIntentServiceName = mIntentServiceClass.getName();
    }

    private static void checkService(Class cls) {
        if(cls==null) {
            throw new NullPointerException("WebServiceManager.checkService() cls is null");
        }

        try {
            Class.forName(cls.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "checkService: ", e);
        }
    }

    /**
     * 对外提供初始化服务，同进程只能被应用Application绑定一次
     * @param context
     * @param listener
     */
    public void init(Context context, OnServiceConnectListener listener) {
        if(context==null) throw new NullPointerException("WebServiceManager.bind's context is NULL!");
        if(listener==null) throw new NullPointerException("WebServiceManager.bind's listener is NULL!");
        if(!(context instanceof Application)) throw new IllegalArgumentException("WebServiceManager.bind's context needs Application!");

        if(this.mContext!=null) {
            Log.i(TAG, "bind: Binded!!!");
        }

        this.mContext = context;
        this.mOnServiceConnectListener = listener;

        bind();
    }

    private void bind() {
        Intent intent = new Intent(mContext, WebService.class);
        mContext.bindService(intent, svsCon, Service.BIND_AUTO_CREATE);
    }

    ServiceConnection svsCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            webService = IWeb.Stub.asInterface(service);
            try {
                webService.setWebClass(webClassName);
                webService.setIntentService(mIntentServiceName);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            mOnServiceConnectListener.onServiceConnected(webService);
            try {
                webService.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            webService = null;
        }
    };

    IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            webService.asBinder().unlinkToDeath(this, 0);
            webService = null;
            Log.i(TAG, "binderDied: rebind...");
            bind();
        }
    };

}
