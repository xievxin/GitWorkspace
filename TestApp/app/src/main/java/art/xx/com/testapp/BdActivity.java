package art.xx.com.testapp;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import art.xx.com.testapp.databinding.FirstBdBinding;

/**
 * Created by Administrator on 2017/07/12 0012.
 */

public class BdActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirstBdBinding binding = DataBindingUtil.setContentView(this, R.layout.first_bd);
        binding.setActivity(this);

        JSONObject jo = new JSONObject();
        try {
            jo.put("url", "baidu.com");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        binding.setJo(jo);
    }

    TextWatcherListener listener = new TextWatcherListener() {
        @Override
        public void onTextChanged(View view, CharSequence s) {
            if(s==null) {
                viewSet.remove(view);
            }else {
                viewSet.add(view);
            }
            if(viewSet.size()==10) {
                //5个输入框都不为空了，改变按钮状态
            }
        }
    };

    Set<View> viewSet = new HashSet<>();

    public void onClick(View view) {
        Toast.makeText(this, "Clicked!!!", Toast.LENGTH_SHORT).show();
    }

    public void show(int msg) {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }


    public class MyPOJO extends BaseObservable {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    class WatcherTextView extends TextView implements TextWatcher {

        private TextWatcherListener mTextWatcherListener;

        public WatcherTextView(Context context) {
            super(context);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mTextWatcherListener.onTextChanged(this, s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public interface TextWatcherListener {
        void onTextChanged(View view, CharSequence s);
    }

    public static class MyUtils {
        public static String char2String(CharSequence s) {
            return s.toString();
        }

        public static String getValue(String s) {
            return s+"HaHa";
        }
    }

}
