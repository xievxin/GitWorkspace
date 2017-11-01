package art.xx.com.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.widget.Button;

import com.xx.process.ChuangkeFit;

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
        btn.setText(MainActivity.value);

        new ChuangkeFit(this);

//        startActivity(new Intent(this, SmsActivity.class));
    }

}
