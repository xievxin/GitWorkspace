package com.ckjr.context;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * LRecylerView支持addHeaderView和addFooterView
 * Created by xin on 2016/2/24.
 */
public class TestActivity extends Activity {

	private String data[] = new String[40];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		for(int i=0;i<data.length;i++)
			data[i] = i+"_item";

		FrameLayout fl = new FrameLayout(this);
		RecyclerView recyclerView = new RecyclerView(this);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);	//线性，类似ListView
		GridLayoutManager gridManager = new GridLayoutManager(this, 2);	//网格，每行高度一致
		StaggeredGridLayoutManager stagGridManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//瀑布流，错落排布
		recyclerView.setLayoutManager(stagGridManager);
		recyclerView.addItemDecoration(null);	//设置item间距 http://my.oschina.net/yaly/blog/515412
		recyclerView.setAdapter(new TestAdapter());
		fl.addView(recyclerView);

		setContentView(fl);
	}


	class TestAdapter extends RecyclerView.Adapter<TestHolder> {


		@Override
		public TestHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
			View itemView = LayoutInflater.from(TestActivity.this).inflate(R.layout.testlayout, null);
			TestHolder holder = new TestHolder(itemView);
			return holder;
		}

		@Override
		public void onBindViewHolder(TestHolder testHolder, int position) {
			testHolder.tv.setText(data[position]);
		}

		@Override
		public int getItemCount() {
			return data.length;
		}
	}

	class TestHolder extends RecyclerView.ViewHolder {

		public TextView tv;

		public TestHolder(View itemView) {
			super(itemView);
			tv = (TextView) itemView.findViewById(R.id.test_tv);
		}
	}

}
