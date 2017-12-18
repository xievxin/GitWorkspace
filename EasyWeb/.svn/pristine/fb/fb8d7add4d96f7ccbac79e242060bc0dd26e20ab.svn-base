package com.xx.model;

import android.content.ContentValues;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by xievxin on 2017/11/16.
 */

public abstract class ServiceBean {

    private static final String TAG = "ServiceBean";

    public Object obj1;
    public Object obj2;

    private ContentValues values;

    public static <T extends ServiceBean> ServiceBean obtain(Class<T> bean) {
        try {
            return bean.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "obtain: ", e);
        }
        return null;
    }

    public abstract int getType();

    public ServiceBean setValues(ContentValues values) {
        this.values = values;
        return this;
    }

    public ContentValues getValues() {
        return values;
    }

    public ServiceBean setObj1(Object obj1) {
        this.obj1 = obj1;
        return this;
    }

    public ServiceBean setObj2(Object obj2) {
        this.obj2 = obj2;
        return this;
    }

    public void post() {
        EventBus.getDefault().post(this);
    }

    public void postSticky() {
        EventBus.getDefault().postSticky(this);
    }
}
