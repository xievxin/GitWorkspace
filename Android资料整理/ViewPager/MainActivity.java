package com.example.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.adapter.ViewPagerAdapter;

public class MainActivity extends Activity {

	public static ImageView imageView[];
	private static final int PAGE1 = 0;
	private static final int PAGE2 = 1;
	private static final int PAGE3 = 2;
	int images[] = new int[]{
			R.drawable.p01, R.drawable.p02, R.drawable.p03	 
	};
	
	ViewPager viewPager;
	ImageView tab1, tab2, tab3, lineImage;
	EditText testView;
	int pageWidth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		testView = (EditText) findViewById(R.id.testView);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		pageWidth = dm.widthPixels/3;	//dm.widthPixels为当前屏幕分辨率宽度
		
		imageView = new ImageView[images.length];
		for(int i=0;i<images.length;i++){
			imageView[i] = new ImageView(MainActivity.this);
			imageView[i].setBackgroundResource(images[i]);
		}
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(new ViewPagerAdapter());
		viewPager.setOnPageChangeListener(pageChange);
		
		tab1 = (ImageView) findViewById(R.id.tab1);
		tab2 = (ImageView) findViewById(R.id.tab2);
		tab3 = (ImageView) findViewById(R.id.tab3);
		lineImage = (ImageView) findViewById(R.id.imageView1);
		tab1.setOnClickListener(tabOnClick);
		tab2.setOnClickListener(tabOnClick);
		tab3.setOnClickListener(tabOnClick);
	}

	//tab页监听
	private OnClickListener tabOnClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) { 
			switch (v.getId()) {
			case R.id.tab1:
				viewPager.setCurrentItem(PAGE1);
				break;
			case R.id.tab2:
				viewPager.setCurrentItem(PAGE2);
				break;
			case R.id.tab3:
				viewPager.setCurrentItem(PAGE3);
				break;
			default:
				break;
			}
		}
	};
	
	//viewpage监听
	private OnPageChangeListener pageChange = new OnPageChangeListener() {
		Animation animation = null;
		int currentIndex = 0;
		
		@Override
		public void onPageSelected(int positioin) {
			switch (positioin) {
			case PAGE1:
				tab1.setBackgroundResource(R.drawable.tab01_select); 
				if(currentIndex==PAGE2){
					tab2.setBackgroundResource(R.drawable.tab02);
					animation = new TranslateAnimation(pageWidth, 0, 0, 0);
				}if(currentIndex==PAGE3){
					tab3.setBackgroundResource(R.drawable.tab03);
					animation = new TranslateAnimation(pageWidth*2, 0, 0, 0);
				}
				break;
			case PAGE2:
				tab2.setBackgroundResource(R.drawable.tab02_select);
				if(currentIndex==PAGE1){
					tab1.setBackgroundResource(R.drawable.tab01); 
					animation = new TranslateAnimation(0, pageWidth, 0, 0);
				}if(currentIndex==PAGE3){
					tab3.setBackgroundResource(R.drawable.tab03);
					animation = new TranslateAnimation(pageWidth*2, pageWidth, 0, 0);
				}
				break;
			case PAGE3:
				tab3.setBackgroundResource(R.drawable.tab03_select);
				if(currentIndex==PAGE1){
					tab1.setBackgroundResource(R.drawable.tab01); 
					animation = new TranslateAnimation(0, pageWidth*2, 0, 0);
				}if(currentIndex==PAGE2){
					tab2.setBackgroundResource(R.drawable.tab02);
					animation = new TranslateAnimation(pageWidth, pageWidth*2, 0, 0);
				}
				break;
			default:
				break;
			}
			currentIndex = positioin;
			animation.setFillAfter(true);	//图片停在动画结束位置 
			animation.setDuration(300);
			lineImage.startAnimation(animation);
			animation = null; //重置
		} 
		
		@Override
		public void onPageScrolled(int positioin, float arg1, int arg2) {		}
			
		@Override
		public void onPageScrollStateChanged(int arg0) { }
	};
	
}
