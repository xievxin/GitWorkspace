package ndk.xx.com.mylibrary;

import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by xievxin on 2017/11/9.
 */

public class WebService {

    private String clsName;

    public void init(Class cls) {
        clsName = cls.getName();
    }

    public void run() {
        try {
            Class mClass = Class.forName(clsName);
            Object instance = mClass.newInstance();
            Field value = mClass.getDeclaredField("value");
            value.setAccessible(true);
            Log.i("~~", "run: "+value.get(instance));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
