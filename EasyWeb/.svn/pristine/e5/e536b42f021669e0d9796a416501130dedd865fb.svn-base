package com.xx.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xievxin on 2017/11/23.
 */

public class SysUtil {

    private static String currentProcessName;

    public static boolean isEasyWebProcess(Context context) {
        String procName = getCurProcessName(context);
        return procName != null && procName.contains(":easyWeb");
    }

    public static String getCurProcessName(Context context) {
        if(!TextUtils.isEmpty(currentProcessName)) {
            return currentProcessName;
        } else {
            int pid = Process.myPid();
            ActivityManager mActivityManager = (ActivityManager)context.getSystemService("activity");
            if(mActivityManager == null) {
                return "";
            } else {
                List apps = mActivityManager.getRunningAppProcesses();
                if(apps != null && !apps.isEmpty()) {
                    Iterator i$ = apps.iterator();

                    while(i$.hasNext()) {
                        RunningAppProcessInfo appProcess = (RunningAppProcessInfo)i$.next();
                        if(appProcess.pid == pid) {
                            currentProcessName = appProcess.processName;
                            break;
                        }
                    }

                    return currentProcessName;
                } else {
                    String procName = getProcessNameFromProc();
                    return procName;
                }
            }
        }
    }

    private static String getProcessNameFromProc() {
        BufferedReader cmdlineReader = null;

        try {
            cmdlineReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/" + Process.myPid() + "/cmdline")));
            StringBuilder processName = new StringBuilder();

            int e;
            while((e = cmdlineReader.read()) > 0) {
                processName.append((char)e);
            }

            String var3 = processName.toString();
            return var3;
        } catch (Throwable var13) {
            var13.printStackTrace();
        } finally {
            if(cmdlineReader != null) {
                try {
                    cmdlineReader.close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }
            }

        }

        return "";
    }

}
