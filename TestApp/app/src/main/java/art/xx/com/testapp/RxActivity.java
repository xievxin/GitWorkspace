package art.xx.com.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import rx.Observable;

/**
 * Created by Administrator on 2018/02/27 0027.
 */

public class RxActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable.create()
    }
}
