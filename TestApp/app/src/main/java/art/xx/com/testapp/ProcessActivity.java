package art.xx.com.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.widget.Button;

import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;

/**
 * Created by xievxin on 2017/6/28.
 */

public class ProcessActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Log.i("~", "onCreate: "+ Process.myPid());

        final Button btn = (Button) findViewById(R.id.button);
//        btn.setText(MainActivity.value);

//        new ChuangkeFit(this);

//        startActivity(new Intent(this, SmsActivity.class));

        final AppPreferences preferences = new AppPreferences(this);
        btn.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("~", "run: " + preferences.getString("ckjr"));
                } catch (ItemNotFoundException e) {
                    e.printStackTrace();
                }
                btn.postDelayed(this, 250);
            }
        }, 0);
    }

}
