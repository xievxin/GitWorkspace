package com.ckjr.util;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xievxin on 2016/11/30.
 */

public class PermissionUtil {

    private static List<String> dangerPermission = new ArrayList<>();

    static {
        //危险权限需要动态申请
        dangerPermission.add("android.permission.WRITE_CONTACTS");
        dangerPermission.add("android.permission.GET_ACCOUNTS");
        dangerPermission.add("android.permission.READ_CONTACTS");
        dangerPermission.add("android.permission.READ_CALL_LOG");
        dangerPermission.add("android.permission.READ_PHONE_STATE");
        dangerPermission.add("android.permission.CALL_PHONE");
        dangerPermission.add("android.permission.WRITE_CALL_LOG");
        dangerPermission.add("android.permission.USE_SIP");
        dangerPermission.add("android.permission.PROCESS_OUTGOING_CALLS");
        dangerPermission.add("com.android.voicemail.permission.ADD_VOICEMAIL");
        dangerPermission.add("android.permission.READ_CALENDAR");
        dangerPermission.add("android.permission.WRITE_CALENDAR");
        dangerPermission.add("android.permission.CAMERA");
        dangerPermission.add("android.permission.BODY_SENSORS");
        dangerPermission.add("android.permission.ACCESS_FINE_LOCATION");
        dangerPermission.add("android.permission.ACCESS_COARSE_LOCATION");
        dangerPermission.add("android.permission.READ_EXTERNAL_STORAGE");
        dangerPermission.add("android.permission.WRITE_EXTERNAL_STORAGE");
        dangerPermission.add("android.permission.RECORD_AUDIO");
        dangerPermission.add("android.permission.READ_SMS");
        dangerPermission.add("android.permission.RECEIVE_WAP_PUSH");
        dangerPermission.add("android.permission.RECEIVE_MMS");
        dangerPermission.add("android.permission.RECEIVE_SMS");
        dangerPermission.add("android.permission.SEND_SMS");
        dangerPermission.add("android.permission.READ_CELL_BROADCASTS");
    }

    private static String finalStr = "";
    public static void checkPermission(final Activity activity, final CallBack callBack) {
        if(callBack==null)
            throw new NullPointerException("PermissionUtil.checkPermission callback isNull");
        if (Build.VERSION.SDK_INT < 23) {   //Api 6.0以下的不要来捣乱
            callBack.onNonePermissionNeedGrant();
            return;
        }
        finalStr = "";
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(activity.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] permissions = info.requestedPermissions;
            Observable.from(permissions)
                    .filter(new Func1<String, Boolean>() {
                        @Override
                        public Boolean call(String permission) {
                            return dangerPermission.contains(permission) &&
                                    PackageManager.PERMISSION_GRANTED!=ActivityCompat.checkSelfPermission(activity, permission);
                        }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onNext(String s) {
                            finalStr = finalStr + "," + s;
                        }

                        @Override
                        public void onCompleted() {
                            finalStr = finalStr.replaceFirst(",", "");
                            if(CommonUtil.isEmpty(finalStr)) {
                                callBack.onNonePermissionNeedGrant();
                            }else {
                                String[] perArray = finalStr.split(",");
                                ActivityCompat.requestPermissions(activity, perArray, 0);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            callBack.onError(e);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onError(e);
        }
    }

    public interface CallBack {
        public void onError(Throwable e);

        /**
         * 没有权限需要获取
         */
        public void onNonePermissionNeedGrant();
    }
}
