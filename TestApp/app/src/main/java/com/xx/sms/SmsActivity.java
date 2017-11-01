package com.xx.sms;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.FrameLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.tencent.smtt.sdk.TbsReaderView.TAG;

/**
 * Created by xievxin on 2017/9/18.
 */

public class SmsActivity extends Activity {

    private Uri SMS_INBOX = Uri.parse("content://sms/");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout content = new FrameLayout(this);
        content.setBackgroundColor(Color.WHITE);
        setContentView(content);

        Log.i("~", "onCreate: "+ Process.myPid());

        SmsReceiver smsReceiver = SmsReceiver.registerReceiver(this);

//        getContentResolver().registerContentObserver(SMS_INBOX, true, new SmsObserver(new Handler()));

//        content.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RxPermissions rxPermissions = RxPermissions.getInstance(SmsActivity.this);

//                rxPermissions.shouldShowRequestPermissionRationale(SmsActivity.this, Manifest.permission.RECEIVE_SMS)
//                        .subscribe(new Action1<Boolean>() {
//                            @Override
//                            public void call(Boolean aBoolean) {
//                                Log.i(TAG, "shouldShowRequestPermissionRationale: "+aBoolean);
//                            }
//                        });

//                rxPermissions.request(Manifest.permission.RECEIVE_SMS)
//                        .subscribe(new Action1<Boolean>() {
//                            @Override
//                            public void call(Boolean granted) {
//                                Log.i(TAG, "request: "+granted);
//                                boolean aBoolean = shouldShowRequestPermissionRationale(Manifest.permission.RECEIVE_SMS);
//                                Log.i(TAG, "shouldShowRequestPermissionRationale: "+aBoolean);
//                            }
//                        });
//            }
//        });
    }


    class SmsObserver extends ContentObserver {
        public SmsObserver(Handler handler) {
            super(handler);
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            getVerifyCodeFromSms();
        }
    }

    private void getVerifyCodeFromSms() {
        ContentResolver cr = getContentResolver();
        /**
         * address:发件人手机号码
         * date:发件日期
         * read:是否阅读
         * status:状态，-1接受
         * body：短信内容
         */
        String[] projection = new String[] { "address", "body", "date", "type",
                "read" };
        Cursor cursor;
        try {
            cursor = cr
                    .query(SMS_INBOX, projection, null, null, "date desc");
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(cursor.getLong(cursor.getColumnIndex("date")));
                    String date = dateFormat.format(d);
                    StringBuilder sb = new StringBuilder();
                    sb.append(
                            "发件人手机号码："
                                    + cursor.getString(cursor
                                    .getColumnIndex("address")))
                            .append("信息内容："
                                    + cursor.getString(cursor
                                    .getColumnIndex("body")))
                            .append("是否查看:"
                                    + cursor.getString(cursor
                                    .getColumnIndex("read")))
                            .append("类型："
                                    + cursor.getInt(cursor.getColumnIndex("type")))
                            .append(date);
                    Log.i(TAG, "getVerifyCodeFromSms: "+sb.toString());
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
