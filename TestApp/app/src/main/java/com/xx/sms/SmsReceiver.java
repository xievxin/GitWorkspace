package com.xx.sms;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsMessage;
import android.util.Log;

import static android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION;

/**
 * Created by xievxin on 2017/9/18.
 */

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";

    public static SmsReceiver registerReceiver(Activity activity) {
        SmsReceiver smsRcv = new SmsReceiver();
        IntentFilter filter = new IntentFilter(SMS_RECEIVED_ACTION);
        activity.registerReceiver(smsRcv, filter);
        return smsRcv;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED_ACTION)) {
            SmsMessage[] messages = getMessagesFromIntent(intent);
            for (SmsMessage message : messages) {
                Log.i(TAG, message.getOriginatingAddress() + " : "
                        + message.getDisplayOriginatingAddress() + " : "
                        + message.getDisplayMessageBody() + " : "
                        + message.getTimestampMillis());
            }
        }
    }

    public final SmsMessage[] getMessagesFromIntent(Intent intent) {
        Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
        byte[][] pduObjs = new byte[messages.length][];
        for (int i = 0; i < messages.length; i++) {
            pduObjs[i] = (byte[]) messages[i];
        }
        byte[][] pdus = new byte[pduObjs.length][];
        int pduCount = pdus.length;
        SmsMessage[] msgs = new SmsMessage[pduCount];
        for (int i = 0; i < pduCount; i++) {
            pdus[i] = pduObjs[i];
            msgs[i] = SmsMessage.createFromPdu(pdus[i]);
        }
        return msgs;
    }
}
