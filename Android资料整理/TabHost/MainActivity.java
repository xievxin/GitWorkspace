package com.example.xin.tabproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends Activity implements TabHost.OnTabChangeListener{

	public static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TabHost tabHost = $(R.id.tabhost);
		tabHost.setOnTabChangedListener(this);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("tag_1").setIndicator(getTabView(this)).setContent(new MyTabContent(this)));
		tabHost.addTab(tabHost.newTabSpec("tag_2").setIndicator(getTabView(this)).setContent(new MyTabContent(this)));
		tabHost.addTab(tabHost.newTabSpec("tag_3").setIndicator(getTabView(this)).setContent(new MyTabContent(this)));
		tabHost.addTab(tabHost.newTabSpec("tag_4").setIndicator(getTabView(this)).setContent(new MyTabContent(this)));
		for(int i=0;i<tabHost.getTabWidget().getChildCount();i++) {
			tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
		}
	}

	private <E extends View>E $(int id) {
		return (E) findViewById(id);
	}

	@Override
	public void onTabChanged(String tabId) {
		Log.e(TAG, tabId+"");
	}

	private View getTabView(Context context) {
		LinearLayout ll = new LinearLayout(context);
		ll.setPadding(150, 0, 150, 0);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setGravity(Gravity.CENTER_HORIZONTAL);
		ImageView iv = new ImageView(context);
		iv.setBackgroundResource(R.mipmap.ic_launcher);
		ll.addView(iv, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		TextView tv = new TextView(context);
		tv.setText("taba");
		ll.addView(tv);
		return ll;
	}

	class MyTabContent implements TabHost.TabContentFactory {

		private Context context;
		public MyTabContent(Context context) {
			super();
			this.context = context;
		}

		@Override
		public View createTabContent(String tag) {
			return new TextView(context);
		}
	}
}
