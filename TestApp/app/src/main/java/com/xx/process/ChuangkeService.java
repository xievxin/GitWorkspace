package com.xx.process;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xievxin on 2017/10/30.
 */

public class ChuangkeService extends Service {

    private List<String> mList = new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if(PackageManager.PERMISSION_GRANTED!=checkCallingOrSelfPermission("art.test.per")) {
            return null;
        }
        return newIChuangke();
    }

    private IBinder newIChuangke() {
        return new IChuangke.Stub() {
            @Override
            public void jiaxin() throws RemoteException {
                Log.i("~", "jiaxin: 每人加薪50%");
            }

            @Override
            public void jiaRen(String name) throws RemoteException {
                mList.add(name);
            }

            @Override
            public String getPersonList() throws RemoteException {
                return mList.toString();
            }
        };
    }

}