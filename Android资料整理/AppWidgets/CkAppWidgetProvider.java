package com.example.xin.test;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by xin on 2016/3/21.
 */
public class CkAppWidgetProvider extends AppWidgetProvider {

	private final String Click_Btn = "com.xievxin.clickclearbtn";

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		String action = intent.getAction();
		if(action.equals(Click_Btn)) {
			Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
//		for(int i=0;i<appWidgetIds.length;i++) {
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.example_appwidget);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(Click_Btn), PendingIntent.flag);
			remoteViews.setTextViewText(R.id.tv_widget, "fff");
			remoteViews.setOnClickPendingIntent(R.id.btn_clear, pendingIntent);
			appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
//		}
	}
}
