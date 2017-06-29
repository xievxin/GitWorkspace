package com.example.testui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.adapter.MyAdapter;
import com.example.fragment.FirstFragment;
import com.example.fragment.MyFragment;
import com.example.fragment.SecondFragment;
import com.example.fragment.ThirdFragment;
import com.slidingmenu.lib.SlidingMenu;

public class MainActivity extends FragmentActivity{

	private Fragment fragment;
	private int lastClick = -1;
	
	SlidingMenu slidingmenu;
	ListView listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);// 只有一个LinearLayout

		//原layout上的view替换不掉，因此用一个fragment展示原layout上的ViewGroup
		fragment = new MyFragment();
		getFragmentManager().beginTransaction().replace(R.id.main, fragment).commit();
		
		//侧边菜单
		slidingmenu = new SlidingMenu(this);
		slidingmenu.setMode(SlidingMenu.LEFT);//设置左滑菜单 
		slidingmenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置要使菜单滑动，触碰屏幕的范围为全屏 
		slidingmenu.setShadowDrawable(R.drawable.p03);//阴影图片
		slidingmenu.setShadowWidth(5);//阴影图片的宽度 
		slidingmenu.setBehindWidth(300);//侧滑菜单宽度
		slidingmenu.setFadeDegree(0.35f);//滑动时的渐变程度 
		slidingmenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//使SlidingMenu附加在Activity侧滑菜单上
		slidingmenu.setMenu(R.layout.leftmenu);
		slidingmenu.showMenu();
		
		//侧滑菜单为ListView
		listview = (ListView) findViewById(R.id.listView);
		listview.setAdapter(new MyAdapter(MainActivity.this));
		listview.setOnItemClickListener(listViewItemClickListener);
	}

	/**
	 * 点击item切换页面
	 */
	private OnItemClickListener listViewItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterview, View view, int position, long id) {
			if(lastClick==position){
				return;
			}
			lastClick = position;
			switch (position) {
			case 0:
				fragment = new FirstFragment();//extends Fragment重写OnCreateView
				break;
			case 1:
				fragment = new SecondFragment();
				break;
			case 2:
				fragment = new ThirdFragment();
				break;
			default:
				break;
			}
			getFragmentManager().beginTransaction().replace(R.id.main, fragment).commit();
		}
	};
	
}
