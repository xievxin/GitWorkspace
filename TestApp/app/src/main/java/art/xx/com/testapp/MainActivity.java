package art.xx.com.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by xievxin on 2017/6/28.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        final Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setText("aaa");

                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("url", "https://www.baidu.com/");
                startActivity(intent);
//                Intent intent1 = new Intent(MainActivity.this, WebActivity.class);
//                intent1.putExtra("url", "http://www.qq.com/");
//                startActivity(intent1);
            }
        });
    }
}
