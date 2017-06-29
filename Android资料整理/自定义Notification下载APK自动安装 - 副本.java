多种Notification：
http://blog.csdn.net/itachi85/article/details/50096609


Notification.Builder builder = new Notification.Builder(this);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pendingIntent);
		builder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
				.setContentText("content")
				.setContentTitle("Title")
				.setSmallIcon(R.drawable.ic_launcher)
				.setWhen(System.currentTimeMillis());

		Notification notification = builder.build();
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.flags |= Notification.FLAG_ONLY_ALERT_ONCE; //发起Notification后，铃声和震动均只执行一次

		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(22, notification);