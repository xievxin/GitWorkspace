package com.example.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	//android:listSelector="#00000000" 可以去除选中时的黄色底色
	//android:cacheColorHint="#00000000"  背景设置为透明，防止滑动时，白屏 
	ExpandableListView expandableListView;
	
	private String[] group = new String[]{
			"第一组", "第二组", "第三组", "第四组"
	};
	private int[] images = new int[]{
		R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4
	};
	private String[][] children = new String[][]{
			{"赵11","赵12","赵13"},
			{"赵21","赵22","赵23","赵24"},
			{"赵31","赵32"},
			{"赵41"}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ExpandableListAdapter exadapter = new BaseExpandableListAdapter() {
			//数量
			@Override
			public int getGroupCount() {
				return group.length;
			}
			
			@Override
			public int getChildrenCount(int position) {
				return children[position].length;
			}
			//名称
			@Override
			public Object getGroup(int position) {
				return group[position];
			}
			
			@Override
			public Object getChild(int groupPosition, int childPosition) {
				return children[groupPosition][childPosition];
			}
			//ID
			@Override
			public long getGroupId(int position) {
				return position;
			}
			
			@Override
			public long getChildId(int groupPosition, int childPosition) {
				return childPosition;
			}
			//View
			@Override
			public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
				LinearLayout ll = new LinearLayout(MainActivity.this);
				TextView tv = new TextView(MainActivity.this);
				tv.setText(group[groupPosition]+"("+children[groupPosition].length+")");
				tv.setTextSize(25);
				ll.addView(tv);
				ll.setPadding(100, 5, 5, 5);
				return ll;
			}
			
			@Override
			public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {
				LinearLayout ll = new LinearLayout(MainActivity.this);
				ll.setOrientation(LinearLayout.HORIZONTAL);
				//头像
				ImageView imageView = new ImageView(MainActivity.this);
				imageView.setBackgroundResource(images[childPosition]);
				ll.addView(imageView);
				//网名和个性签名
				LinearLayout newll = new LinearLayout(MainActivity.this);
				newll.setOrientation(LinearLayout.VERTICAL);
				TextView name = new TextView(MainActivity.this);
				name.setText(children[groupPosition][childPosition]);
				name.setTextSize(15);
				name.setGravity(Gravity.CENTER_HORIZONTAL);
				newll.addView(name);
				TextView sign = new TextView(MainActivity.this);
				sign.setText("【离线】");
				newll.addView(sign);
				ll.addView(newll);
				return ll;
			}
			//随意的配置成true
			@Override
			public boolean isChildSelectable(int arg0, int arg1) {
				return true;
			}
			
			@Override
			public boolean hasStableIds() {
				return true;
			}
		};

		
		expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
		expandableListView.setAdapter(exadapter);
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
					int childPosition, long id) {
				Toast.makeText(MainActivity.this, 
						children[groupPosition][childPosition]+"不在线", Toast.LENGTH_SHORT).show();;
				return false;
			}
		});
	}


}
