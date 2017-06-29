package com.example.adapter;

import com.example.viewpager.MainActivity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewPagerAdapter extends PagerAdapter{

	@Override
	public int getCount() {
		return MainActivity.imageView.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(MainActivity.imageView[position]);
		return MainActivity.imageView[position];
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(MainActivity.imageView[position]);
	}

}
