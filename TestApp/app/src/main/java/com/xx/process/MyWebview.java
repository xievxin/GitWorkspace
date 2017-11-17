package com.xx.process;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.WebView;

/**
 * Created by xievxin on 2017/11/6.
 */

public class MyWebview extends WebView implements Parcelable{

    public MyWebview(Context context) {
        this(context, null);
    }

    public MyWebview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    protected MyWebview(Parcel in) {
        super(null);
    }

    public static final Creator<MyWebview> CREATOR = new Creator<MyWebview>() {
        @Override
        public MyWebview createFromParcel(Parcel in) {
            return new MyWebview(in);
        }

        @Override
        public MyWebview[] newArray(int size) {
            return new MyWebview[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
