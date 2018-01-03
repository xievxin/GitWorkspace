package art.xx.com.testapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;

import net.grandcentrix.tray.AppPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

/**
 * Created by xievxin on 2017/6/28.
 */

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    public static String value = "who's sb?";
    public static final String frgTag = "myfragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.i("~", "onCreate: "+ Process.myPid());

        startActivity(new Intent(this, ProcessActivity.class));

        final AppPreferences appPreferences = new AppPreferences(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(1_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String str="_" + getRandomStr();
                    appPreferences.put("ckjr", str);
                    Log.w("~", "run: " + str);
                }
            }
        }).start();

    }

    private String getRandomStr() {
        Random rd = new Random();
        return rd.nextInt(99)+"";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: "+requestCode+resultCode);
    }

    public static class MyFragment extends Fragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.i(TAG, "MyFragment onCreate()");
            this.startActivityForResult(new Intent(getActivity(), ProcessActivity.class), 0);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            Log.i(TAG, "MyFragment onActivityResult(): "+requestCode+resultCode);
            Activity activity = getActivity();
            try {
                Method mtd = activity.getClass().getDeclaredMethod("onActivityResult", int.class, int.class, Intent.class);
                mtd.setAccessible(true);
                mtd.invoke(activity, requestCode, resultCode, data);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
