package com.xx.process;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by xievxin on 2017/10/30.
 */

public class ChuangkeFit {

    public ChuangkeFit(Activity activity) {
        Intent intent = new Intent(activity, ChuangkeService.class);
        activity.bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IChuangke ck = IChuangke.Stub.asInterface(service);
            try {
                ck.jiaRen("xiexin");
                Log.i("~", "onServiceConnected: "+ck.getPersonList());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("~", "onServiceDisconnected: " + name);
        }
    };

}
