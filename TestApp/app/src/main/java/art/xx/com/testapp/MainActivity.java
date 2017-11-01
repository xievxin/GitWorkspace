package art.xx.com.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.widget.Button;

/**
 * Created by xievxin on 2017/6/28.
 */

public class MainActivity extends Activity {

    public static String value = "who's sb?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Log.i("~", "onCreate: "+ Process.myPid());

        value = "tqb";

        final Button btn = (Button) findViewById(R.id.button);

        startActivity(new Intent(this, ProcessActivity.class));
    }

}
