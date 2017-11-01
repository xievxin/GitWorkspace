package art.xx.com.testapp;

import android.app.Application;
import android.util.Log;

/**
 * Created by xievxin on 2017/10/30.
 */

public class ArtApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("~", "ArtApplication.onCreate: ");
    }
}
