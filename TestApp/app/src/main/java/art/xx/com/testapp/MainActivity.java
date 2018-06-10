package art.xx.com.testapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

        final Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RxActivity.class));
                finish();
            }
        });


//        Fragment fragment = new MyFragment();
//        getFragmentManager().beginTransaction()
//                .add(fragment, frgTag)
//                .commitAllowingStateLoss();
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
