package com.xievxin.gtapps;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class AppFinder {

    private static final String TAG = "AppFinder";

    public static String[] needsPermissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static List<String> scanInstallerApps(Context context) {
        List<String> pkgList = new ArrayList<>();
        List<ApplicationInfo> infoList = context.getPackageManager().getInstalledApplications(0);
        for (ApplicationInfo info : infoList) {
            pkgList.add(info.packageName);
        }
        return pkgList;
    }

    public static List<String> scanGTApps(Context context) {
        context = context.getApplicationContext();
        File file = new File("/sdcard/libs/");
        if (!file.exists()) {
            return null;
        }
        File[] fileArr = file.listFiles();
        if (fileArr == null) {
            return null;
        }
        List<String> appList = new ArrayList<>();
        for (File f : fileArr) {
            if (!f.isDirectory() && f.getName().contains(".db")) {
                appList.add(f.getName().replace(".db", ""));
            }
        }

        // 不清楚卸载了，此目录下还有没有.db文件，double check一下
        List<String> applications = scanInstallerApps(context);
        for (Iterator<String> it = appList.iterator(); it.hasNext(); ) {
            if (!applications.contains(it.next())) {
                it.remove();
            }
        }
        return appList;
    }

    /**
     * 获取动态Activity
     * @param pkg
     * @return
     */
    public static String getDynamicActName(Context context, String pkg) {
        return getActivityFromFile(context.getApplicationContext(), pkg);
    }

    /**
     * 获取动态Service
     * @param pkg
     * @return
     */
    public static String getDynamicSvcName(String pkg) {
        return getDecryptData(pkg);
    }

    public static boolean isServiceWorked(Context context, String pkgName, String serviceName) {
        if (TextUtils.isEmpty(serviceName)) {
            return false;
        }
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infoList = manager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo info : infoList) {
            Log.i(TAG, "isServiceWorked: " + info.service.getClassName());
            if (pkgName.equals(info.service.getPackageName()) && info.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }


    /**
     *  解析 动态Activity的方法.
     *
     */
    private static String getActivityFromFile(Context context, String pkgName) {
        String activity = null;

        try {
            byte[] bytes = getDataFromFile("/sdcard/libs/" + pkgName + ".db");
//            byte[] bytes = getDataFromFile("/sdcard/libs/" + pkgName + ".bin");

            if (bytes == null) {
                Log.i("GBDUtils", pkgName + ".db not exist");
//                GBDLog.log("GBDUtils", pkgName + ".db not exist");
                return null;
            }

            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();

            ArrayList<String> keys = new ArrayList<String>();
            keys.add(getMD5Str(imei));
            keys.add(getMD5Str(""));
            keys.add(getMD5Str("000000000000000"));
            keys.add(getMD5Str("cantgetimei"));

            // IMEI2 暂不考虑
//            String imei2 = SimCardUtils.getDeviceId(1, GBDRuntimeInfo.context);
//            if (!TextUtils.isEmpty(imei2)) {
//                keys.add(StringUtils.getMD5Str(imei2));
//            }

            String pattern = "[\\.:0-9a-zA-Z\\|]+";
            String[] arr = null;

            for (String key : keys) {
                String temp = new String(RC4Utils.dencrypt(bytes, key));

                Log.i("GBDUtils", "parse db data = " + temp);
                if (Pattern.matches(pattern, temp)) {
                    arr = temp.split("\\|");
                    break;
                }
            }

            if (arr != null && arr.length > 3) {
                activity = arr[3];
                if (activity != null) {
                    activity = activity.equals("null") ? null : activity;
                }
            }
        } catch (Exception e) {
            // #debug
            e.printStackTrace();
        }

        return activity;
    }



    private static byte[] getDataFromFile(String fileName) {
        if (!new File(fileName).exists()) {
            return null;
        }

        byte[] bytes = null;

        FileInputStream in = null;
        ByteArrayOutputStream baos = null;
        byte[] buffer = new byte[1024];

        try {
            in = new FileInputStream(fileName);
            baos = new ByteArrayOutputStream();
            int len;
            while ((len = in.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            bytes = baos.toByteArray();
        } catch (Exception e) {
//            GBDLog.e(e);
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
//                    GBDLog.e(e);
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e) {
//                    GBDLog.e(e);
                    e.printStackTrace();
                }
            }
        }

        return bytes;
    }



    // MD5加密
    private static String getMD5Str(String sourceStr) {
        byte[] source = sourceStr.getBytes();
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        java.security.MessageDigest md = null;
        try {
            md = java.security.MessageDigest.getInstance("MD5");
        } catch (Exception e) {
//            GBDLog.e(e);
            e.printStackTrace();
        }
        if (md == null) {
            return null;
        }

        md.update(source);
        byte[] tmp = md.digest();
        char[] str = new char[16 * 2];
        int k = 0;
        for (int i = 0; i < 16; i++) {
            byte byte0 = tmp[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }

        return new String(str);
    }

    // 解码 .bin 文件，获取动态 Service
    private static String getDecryptData(String pkgName) {

        byte[] data = getDataFromFile("/sdcard/libs/" + pkgName + ".bin");
//        byte[] data = getDataFromFile("/sdcard/libs/" + pkgName + ".db");

        if (data == null || data.length < 16) {
            return null;
//            return;
        }

        byte[] desData = new byte[data.length - 16];
        System.arraycopy(data, 8, desData, 0, data.length - 16);

        return new String(unGzip(desData));
    }


    // gzip解压缩
    private static byte[] unGzip(byte[] srcArray) {
        ByteArrayInputStream in = new ByteArrayInputStream(srcArray);
        GZIPInputStream gzipIn = null;
        ByteArrayOutputStream out = null;
        byte[] result = null;
        try {
            gzipIn = new GZIPInputStream(in);
            out = new ByteArrayOutputStream();
            int ch = 0;
            while ((ch = gzipIn.read()) != -1) {
                out.write(ch);
            }
            result = out.toByteArray();

        } catch (Throwable e) {
            // #debug
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    // #debug
                    e.printStackTrace();
                }
            }
            if (gzipIn != null) {
                try {
                    gzipIn.close();
                } catch (Exception e) {
                    // #debug
                    e.printStackTrace();
                }
            }
            try {
                in.close();
            } catch (Exception e) {
                // #debug
                e.printStackTrace();
            }
        }
        return result;
    }
}
