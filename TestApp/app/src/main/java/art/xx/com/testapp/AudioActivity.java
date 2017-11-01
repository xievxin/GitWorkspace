package art.xx.com.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/07/17 0017.
 */

public class AudioActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new FrameLayout(this));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                Toast.makeText(this, "volup", Toast.LENGTH_SHORT).show();
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Toast.makeText(this, "voldown", Toast.LENGTH_SHORT).show();
                break;
            case KeyEvent.KEYCODE_VOLUME_MUTE:
                Toast.makeText(this, "volmute", Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
        return super.onKeyDown(keyCode, event);
    }

}
